import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * This class contains functions that checks for pattern matching, checks and replace string contents, and splits by a predesignated delimiter.
 *
 * @author Brandon Blank
 * @version 0.01
 */
public class PatternCheckMatchAndSplit
{
    private Pattern amount_V1;
    private Pattern poundPattern;
    private Pattern dashPattern;
    
    /**
     * Default constructor for objects of the Splitter class. Creates the following patterns:
     * 
     * amount_V: ***.**_V 
     * pound: #****
     * dash: [space]-[space][space]
     */
    public PatternCheckMatchAndSplit()
    {
        amount_V1 = Pattern.compile("[0-9]*[0-9]*.[0-9]*\\_V"); //Needs additional fine tuning to work with amounts greater than 99.99_V
        poundPattern = Pattern.compile("\\#[0-9]*");
        dashPattern = Pattern.compile("\\s-\\s\\s");
    }
    
    /**
     * Deprecated: Replaced by checkAndReplace_pound function.
     */
    public String[] splitByPound(String str)
    {
        String[] ar = str.split("#");

        return ar;
    }
    
    /**
     * Checks the dash pattern for a match in the provided argument. Returns true if found, false if not found.
     */
    public boolean check_dash(String str)
    {
        boolean b = false;
        Matcher match1 = dashPattern.matcher(str);

        if (match1.find())
        {
            match1 = null;
            
            return true;
        }

        return false;
    }
    
    /**
     * Checks the dash pattern for a match in the provided argument. This method returns the altered string if found, otherwise it returns null.
     */
    public String checkAndReplace_dash(String str)
    {
        String s;
        Matcher match1 = dashPattern.matcher(str);

        if (match1.find())
        {           
            s = match1.replaceFirst("");
            match1 = null;

            return s;
        }

        return null;
    }
    
    /**
     * Checks the pound pattern for a match in the provided argument. Returns true if found, false if not found.
     */
    public boolean check_pound(String str)
    {
        boolean b = false;
        Matcher match1 = poundPattern.matcher(str);

        if (match1.find())
        {
            match1 = null;
            
            return true;
        }

        return false;
    }
    
    /**
     * Checks the pound pattern for a match in the provided argument. This method returns the altered string if found, otherwise it returns null.
     */
    public String checkAndReplace_pound(String str)
    {
        String s;
        Matcher match1 = poundPattern.matcher(str);

        if (match1.find())
        {           
            s = match1.replaceFirst("");
            match1 = null;

            return s;
        }

        return null;
    }
    
    /**
     * Splits the provided string by commas and returns the result as an array. 
     */
    public String[] splitByComma(String str)
    {
        String[] ar = str.split(",");

        return ar;
    }

    /**
     * Deprecated: Needs to be updated and replaced. 
     */
    public String[] splitBySpace(String str)
    {
        String[] ar = str.split(" ");

        return ar;
    }
    
    /**
     * Deprecated: Needs to be updated and replaced.
     */
    public boolean check_StarBracketCombo(String str)
    {
        boolean b = false;

        if (str.contains("*<") || str.contains(">*"))
        {
            return true;
        }

        return false;
    }
    
    /**
     * Checks the amount_V pattern for a match in the provided argument. Returns true if found, false if not found.
     */
    public boolean check_VAmount(String str)
    {
        boolean b = false;
        Matcher match1 = amount_V1.matcher(str);

        if (match1.find())
        {
            match1 = null;
            
            return true;
        }

        return false;
    }
    
    /**
     * Checks the amount_V pattern for a match in the provided argument. This function returns the altered string if found, otherwise it returns null.
     */
    public String checkAndReplace_VAmount(String str)
    {
        String s;
        Matcher match1 = amount_V1.matcher(str);

        if (match1.find())
        {           
            s = match1.replaceFirst("");
            match1 = null;

            return s;
        }

        return null;
    }
}
