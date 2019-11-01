package Main;



import StringOp.RandomStringGenerator;
import StringOp.PatternCheckMatchAndSplit;
import java.io.*;
import java.util.Scanner;
import java.sql.Connection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * A collection of methods used for parsing a CSV file, generating an output file, and parsing the fields into a database.
 *
 * @author Brandon Blank
 * @version 0.01
 */
public class CSVParser
{
    private File f;

    /**
     * Constructor: Takes a File as input.
     * @param fi
     */
    public CSVParser(File fi)
    {
        f = fi;
    }

    /**
     * Creates a new csv file using the RandomStringGenerator() class to provide the file name.
     */
    private File createNewFileWithRandomFileName()
    {
        RandomStringGenerator gen = new RandomStringGenerator();

        StringBuilder randomStr;
        File testFile;

        testFile = new File(f.getParent() + "\\" + gen.createRandomString() + ".csv");
        try
        {
            //Checks if the file name exists. If it already exists, then a new name will be randomly generated
            while (testFile.exists())
            {
                gen = new RandomStringGenerator();
                randomStr = new StringBuilder(f.getParent() + "\\" + gen.createRandomString());
                randomStr.append(".csv");

                gen.close();
                randomStr = null;

                testFile = new File(randomStr.toString());
            }

            testFile.createNewFile();

            return testFile;
        }
        catch(IOException e)
        {
            //Logger message

            return null;
        }

    }

    /**
     * Creates a new file to append the changed contents. On exception thrown, the file that is created is deleted.
     * 
     * The second column is evaluated for matches to known useless data, and if found then removed from the string. 
     * The new string is then inserted as a line in the new csv file.
     * 
     * @return boolean
     */
    public boolean readFileAndOutputToFile()
    {
        String temp;
        PrintWriter pw;
        File newFile;
        FileReader fRead;
        Scanner inp;

        //Prepare output file to be created
        newFile = createNewFileWithRandomFileName();

        //If file name fails to be made, it is null
        if (newFile == null)
        {
            return false;
        }

        try
        {
            //StringBuilder temp;
            String[] strArr;
            String[] tempArr;

            System.out.println(newFile.toString());
            pw = new PrintWriter(newFile);

            //Prepare for data manipulation
            PatternCheckMatchAndSplit sp = new PatternCheckMatchAndSplit();

            //Prepare input file to be read
            fRead = new FileReader(f);
            inp = new Scanner(fRead);

            while (inp.hasNextLine())
            {
                temp = inp.nextLine()+"\n";
                
                if (!temp.equals("\\n"))
                {

                    strArr = temp.split(",");
                   
                    //Verify that the formatting is correct
                    if (!verifyRequiredFormat(strArr))
                    {
                        newFile.delete();
                        return false;
                    }

                    //Assumption: strArr[1] contains the transaction descriptions
                    if (sp.check_pound(strArr[1]))
                    {
                        strArr[1] = sp.checkAndReplace_pound(strArr[1]);
                    }

                    if (sp.check_VAmount(strArr[1]))
                    {
                        strArr[1] = sp.checkAndReplace_VAmount(strArr[1]);
                    }

                    if (sp.check_dash(strArr[1]))
                    {
                        strArr[1] = sp.checkAndReplace_dash(strArr[1]);
                    }

                    StringBuilder sb;
                                   
                    //Checks the length of the array, then applies the correct string generation by the length
                    switch (strArr.length)
                    {
                        case 5: //Case for | Date | Transaction Description | Optional | Optional | Optional
                            sb = new StringBuilder(strArr[0] + "," + strArr[1] + "," + strArr[2] + "," + strArr[3] + "," + strArr[4]);
                            break;
                        
                        case 4: //Case for | Date | Transaction Description | Optional | Optional
                            sb = new StringBuilder(strArr[0] + "," + strArr[1] + "," + strArr[2] + "," + strArr[3]);
                            break;
                            
                        default: //Case for unknown cases. Less efficient than declared code.
                        
                            sb = new StringBuilder();
                        
                            for(int i = 0; i < strArr.length; i++)
                            {
                                sb.append(strArr[i]);

                                if (i != strArr.length-1)
                                {
                                    sb.append(",");
                                }
                            }
                            break;
                    }
                  
                    pw.print(sb);
                    sb = null;
                }
                else
                {
                    throw new Exception();
                }
            }

            //Close resources used
            pw.close();
            fRead.close();
            inp.close();
        }
        catch(Exception e)
        {
            //Delete the incomplete file on exception throw, then return false.
            newFile.delete();
            return false;
        }

        return true;
    }
    
    /**
     * Sets the source file data will be grabbed from.
     */
    public void setFile(File fi)
    {
        f = fi;
    }

    /**
     * Reads in a csv file, and checks for a particular patterns. If found, the patterns are removed from the record and sent to a database table.
     */
    public void readFileAndOutputToDB(Connection db)
    {

    }
    
    /**
     * Verifies that the record contains fields in the following order:
     * Date | Transaction Description | Optional... | Optional...
     * 
     * @param str String array
     * @return boolean
     */
    private boolean verifyRequiredFormat(String[] str)
    {
        //Variables
        boolean numberCheck1;
        boolean numberCheck2;
        boolean numberCheck3;
        boolean numberCheck4;

        //Check if the array length is less than 2
        if (str.length < 2)
        {
            return false;
        }
        
        //Trim leading and exiting spaces to ensure accuracy
        str[0] = str[0].trim();
        
        //Verify [0] is a date
        //Format 1: DD/MM/YYYY or MM/DD/YYYY
        Pattern dateF1 = Pattern.compile("[0-9][0-9]?\\/[0-9][0-9]?\\/[0-9][0-9][0-9][0-9]");
        Matcher dateMatcher1 = dateF1.matcher(str[0]);

        //Format 2: DD-MM-YYYY or MM-DD-YYYY
        Pattern dateF2 = Pattern.compile("[0-9][0-9]?-[0-9][0-9]?-[0-9][0-9][0-9][0-9]");
        Matcher dateMatcher2 = dateF2.matcher(str[0]);

        //Format 3: YYYY/DD/MM/ or YYYY/MM/DD/YYYY
        Pattern dateF3 = Pattern.compile("[0-9][0-9][0-9][0-9]\\/[0-9][0-9]?\\/[0-9][0-9]?");
        Matcher dateMatcher3 = dateF3.matcher(str[0]);

        //Format 4: YYYY-DD-MM or YYYY-MM-DD
        Pattern dateF4 = Pattern.compile("[0-9][0-9][0-9][0-9]-[0-9][0-9]?-[0-9][0-9]?");
        Matcher dateMatcher4 = dateF4.matcher(str[0]);
        
        //Determine match
        numberCheck1 =  dateMatcher1.matches();
        numberCheck2 =  dateMatcher2.matches();
        numberCheck3 =  dateMatcher3.matches();
        numberCheck4 =  dateMatcher4.matches();
        
        //As long as one match is found, then the statement will be false, otherwise return false
        if (!(dateMatcher1.matches() || dateMatcher2.matches() || dateMatcher3.matches() || dateMatcher4.matches()))
        {
            return false;
        }
        //Verify that [1] is not an invalid transaction description. If invalidf, then return false, otherwise return true.
        return !((str[1].equals("") || str[1].equals(null)));
    }
}
