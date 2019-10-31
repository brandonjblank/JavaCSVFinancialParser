package Main;

import StringOp.RandomStringGenerator;
import StringOp.PatternCheckMatchAndSplit;
import java.io.*;
import java.util.Scanner;
import java.sql.Connection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * A collection of methods used for parsing financial statements.
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
     * The first column is evaluated for matches to known useless data, and if found then removed from the string.
     * 
     * @return boolean
     */
    public boolean readFileAndOutputToFile()
    {
        //Variables
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
            //Logger: File not found
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
                
                System.out.println(temp);
                
                if (!temp.equals("\\n"))
                {
                    //Split line into array to better isolate first column
                    strArr = temp.split(",");
                   
                    //Assumption: strArr[0] contains the transaction descriptions
                    if (sp.check_pound(strArr[0]))
                    {
                        strArr[0] = sp.checkAndReplace_pound(strArr[0]);
                    }

                    if (sp.check_VAmount(strArr[0]))
                    {
                        strArr[0] = sp.checkAndReplace_VAmount(strArr[0]);
                    }

                    if (sp.check_dash(strArr[0]))
                    {
                        strArr[0] = sp.checkAndReplace_dash(strArr[0]);
                    }

                    StringBuilder sb = new StringBuilder();
                    
                    for(int i = 0; i < strArr.length; i++)
                    {
                        sb.append(strArr[i]);

                        if (i != strArr.length-1)
                        {
                            sb.append(",");
                        }
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
     * @param fi
     */
    public void setFile(File fi)
    {
        f = fi;
    }

    /**
     * Reads in a csv file, and checks for a particular patterns.If found, the patterns are removed from the record and sent to a database table.
     * @param db
     */
    public void readFileAndOutputToDB(Connection db)
    {
        //To be created
    }

    
    
    
    /**
     * Deprecated
     * 
     * 
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
