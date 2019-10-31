package StringOp;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Brandon Blank
 * @version 0.01
 */
public class FormatIdentifier {
    
    private String dateFormatTyoe;
    
    private boolean verifyDateFormat1Type1()
    {
        
        return false;
    }
    
    private boolean verifyDateFormat1Type2()
    {
        
        return false;
    }
    
    private boolean verifyDateFormat2Type1()
    {
        
        return false;
    }
    
    private boolean verifyDateFormat2Type2()
    {
        
        return false;
    }
    
    private boolean verifyDateFormat3Type1()
    {
        
        return false;
    }
    
    private boolean verifyDateFormat3Type2()
    {
        
        return false;
    }
    
    private boolean verifyDateFormat4Type1()
    {
        
        return false;
    }
    
    private boolean verifyDateFormat4Type2()
    {
        
        return false;
    }
    
    
    /**
     * Returns a number that identifies the format the the
     * 
     * Valid object format: Date | String                  | Double      | Double   | (optional) Double
     * Valid type format  : Date | Transaction Description | Withdrawals | Deposits | (optional) Balance
     * 
     * @param str String array
     * @return boolean
     */
    private int identifyFormat(int len)
    {
        
        if (len > 2)
        {
            //Assume format: Date | Transaction Description | Optional | Optional... 
            return 3;
        }
        else if (len  == 2)
        {
             //Assume format: Transaction Description | Item
            return 2;
        }
        else if (len  == 1)
        {
            //Assume format: Transaction Description
            return 1;
        }
        else
        {
            return 0; //length is zero or less
        }
        
    }
    
    /*    
        //Verify [0] is a date.
        
        //Checks pattern dd/mm/yyyy and mm/dd/yyyy
        Pattern dateF1 = Pattern.compile("[0-9][0-9]?\\/[0-9][0-9]?\\/[0-9][0-9][0-9][0-9]");
        Matcher dateMatcher1 = dateF1.matcher(str[0]);

        //Checks pattern dd-mm-yyyy and mm-dd-yyyy
        Pattern dateF2 = Pattern.compile("[0-9][0-9]?-[0-9][0-9]?-[0-9][0-9][0-9][0-9]");
        Matcher dateMatcher2 = dateF2.matcher(str[0]);

        //Checks pattern yyyy/dd/mm and yyyy/mm/dd
        Pattern dateF3 = Pattern.compile("[0-9][0-9][0-9][0-9]\\/[0-9][0-9]?\\/[0-9][0-9]?");
        Matcher dateMatcher3 = dateF3.matcher(str[0]);

        //Checks pattern yyyy-dd-mm and yyyy-mm-dd
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
    */
}
