

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
    //private AppSettings sett;

    /**
     * Constructor. Takes a File as input.
     */
    public CSVParser(File fi)
    {
        f = fi;
    }

    /**
     * Creates a csv file using the RandomStringGenerator() class.
     */
    private File createRandomFileName()
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
        catch(Exception e)
        {
            //Logger message

            return null;
        }

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
    public void parseAndInputToDB(Connection db)
    {

    }

    /**
     * Reads in a csv file, and checks for a particular patterns. If found, the patterns are removed from the record.
     * 
     * When the process is complete, return true on successful read and write. Return false on exception throw.
     * 
     * The file that was created is deleted when the exception is thrown. May require protection permission to interact with the parent directory (ie. Desktop).
     */
    
    /**
     * Currently only works for transaction records that have 5 columns.
     * 
     * @return boolean
     */
    public boolean readFileAndOutput()
    {
        String temp;
        PrintWriter pw;
        File newFile;
        FileReader fRead;
        Scanner inp;

        //Prepare output file to be created
        newFile = createRandomFileName();

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
                    if (!verifyRecordFormat(strArr))
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
                        case 5: //Case for Date | Transaction Description | Withdrawals | Deposits | Balance
                            sb = new StringBuilder(strArr[0] + "," + strArr[1] + "," + strArr[2] + "," + strArr[3] + "," + strArr[4]);
                            break;
                        
                        case 4:  //Case for Date | Transaction Description | Withdrawals | Deposits
                            sb = new StringBuilder(strArr[0] + "," + strArr[1] + "," + strArr[2] + "," + strArr[3]);
                            break;
                            
                        default: //Case for unknown cases. Less efficient than declared code.
                        
                            sb = new StringBuilder();
                        
                            for(int i = 0; i < strArr.length; i++)
                            {
                                sb.append(strArr[i]);
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
     * Verifies that the record is in an acceptable format that the program can parse
     * 
     * @param str String array
     * @return boolean
     */
    private boolean verifyRecordFormat(String[] str)
    {
        //Variables
        boolean balanceCheck = false;
        
        //Check if the array length is greater than 5 or less than 4
        if (str.length < 4 || str.length > 5)
        {
            return false;
        }
        
        //Check array length is = 5
        if (str.length == 5)
        {
            balanceCheck = true;
        }
        
        //Verify [0] is a date.
        Pattern dateF1 = Pattern.compile("[0-9][0-9]?\\/[0-9][0-9]?\\/[0-9][0-9][0-9][0-9]");
        Matcher dateMatcher1 = dateF1.matcher(str[0]);

        Pattern dateF2 = Pattern.compile("[0-9][0-9]?-[0-9][0-9]?-[0-9][0-9][0-9][0-9]");
        Matcher dateMatcher2 = dateF2.matcher(str[0]);

        Pattern dateF3 = Pattern.compile("[0-9][0-9][0-9][0-9]\\/[0-9][0-9]?\\/[0-9][0-9]?");
        Matcher dateMatcher3 = dateF3.matcher(str[0]);

        Pattern dateF4 = Pattern.compile("[0-9][0-9][0-9][0-9]-[0-9][0-9]?-[0-9][0-9]?");
        Matcher dateMatcher4 = dateF4.matcher(str[0]);
        
        boolean numberCheck1 =  dateMatcher1.matches();
        boolean numberCheck2 =  dateMatcher2.matches();

        if (!(dateMatcher1.matches() || dateMatcher2.matches() || dateMatcher3.matches() || dateMatcher4.matches()))
        {
            return false;
        }
        
        if (balanceCheck)
        {
             //Verify that [4] is a number
            Pattern numberF = Pattern.compile("[0-9]*\\.[0-9]*\\n");
            Matcher matchNumberF = numberF.matcher(str[4]);

            Pattern numberF2 = Pattern.compile("[0-9]*\\n");
            Matcher matchNumberF2 = numberF2.matcher(str[4]);

            Pattern numberF3 = Pattern.compile("[0-9]*\\n");
            Matcher matchNumberF3 = numberF3.matcher(str[4]);

            Pattern numberF4 = Pattern.compile("-[0-9]*\\.[0-9]*\\n");
            Matcher matchNumberF4 = numberF4.matcher(str[4]);

            Pattern numberF5 = Pattern.compile("-[0-9]*\\n");
            Matcher matchNumberF5 = numberF5.matcher(str[4]);

            Pattern numberF6 = Pattern.compile("-[0-9]*\\n");
            Matcher matchNumberF6 = numberF6.matcher(str[4]);
   
            if (!(matchNumberF.matches() || matchNumberF2.matches() || matchNumberF3.matches() || matchNumberF4.matches() || matchNumberF5.matches() || matchNumberF6.matches()))
            {
                return false;
            }
        }
       
        //Verify that [2] and [3] are numerical fields
        Pattern numberF7 = Pattern.compile("[0-9]*\\.[0-9]*");
        Matcher matchNumberF7 = numberF7.matcher(str[2]);
        
        Pattern numberF8 = Pattern.compile("[0-9]*");
        Matcher matchNumberF8 = numberF8.matcher(str[2]);
        
        Pattern numberF9 = Pattern.compile("[0-9][0-9]?\\n");
        Matcher matchNumberF9 = numberF9.matcher(str[4]);
        
        //Check for debugging
        boolean check1 = matchNumberF7.matches(); //true, false, true
        boolean check2 = matchNumberF8.matches(); //false, true, false
        boolean check3 = str[2].equals(""); // true true, false
        
        if (!(matchNumberF7.matches() || matchNumberF8.matches() || str[2].equals("")))
        {
            return false;
        }
        
        matchNumberF7.reset(str[3]);
        matchNumberF8.reset(str[3]);
        
        //Check for debugging
        check1 = matchNumberF7.matches();
        check2 = matchNumberF8.matches();
        check3 = str[2].equals("");
        
        
        if (!(matchNumberF7.matches() || matchNumberF8.matches() || str[3].equals("")))
        {
            return false;
        }
        
        //Verify that [1] is not invalid
        if(str[1].equals("") || str[1].equals(null) )
        {
            return false;
        }
        
        //All conditions passed
        return true;
    }
}
