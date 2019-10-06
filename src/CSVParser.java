import java.nio.file.Files;
import java.io.*;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
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

                strArr = temp.split(",");

                if (strArr.length > 5)
                {
                    newFile.delete();
                    return false;
                }

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

                //Assumption: Each record only includes the following fields: Date | Transaction Description | Withdrawals | Deposits | Account Balance
                StringBuilder sb = new StringBuilder(strArr[0] + "," + strArr[1] + "," + strArr[2] + "," + strArr[3] + "," + strArr[4]);

                pw.print(sb);
                sb = null;
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
}
