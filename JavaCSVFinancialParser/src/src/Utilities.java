package src;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Contains functions for interacting with JavaFX FileChooser and Alert
 * functions.
 *
 * @author Brandon Blank
 * @version 0.01
 */
public class Utilities {

    /**
     * Return null if the FileChooser is exited or the process is cancelled
     * before selecting a file. Returns a File object if a file is selected.
     *
     * Currently this method does not support multiple extension filters. For
     * more information:
     * https://stackoverflow.com/questions/25189123/javafx-filechooser-extensionfilter-not-working-properly-highlighted-files
     */
    public File getFile(String title, String extensionName, String extension) {
        File f;
        Stage s = new Stage();

        s.setAlwaysOnTop(true);
        s.toFront();
        FileChooser fChooser;

        try {
            fChooser = new FileChooser();
            fChooser.setTitle(title);
            fChooser.getExtensionFilters().addAll(
                    new ExtensionFilter(extensionName, extension));
            f = fChooser.showOpenDialog(s);

            fChooser = null;
            s.close();

            return f;
        } catch (Exception e) {
            fChooser = null;
            s.close();
            return null;
            //Log issue
        }

    }

    /**
     * Sets up all required functions to generate an alert by taking arguments.
     */
    public void createAlert(String strTtitle, String strContent, AlertType aType) {
        Alert a = new Alert(aType);
        a.setTitle(strTtitle);
        a.setContentText(strContent);

        a.showAndWait();

        a = null;

    }

    public String parseDate(String str) {

        String[] d;
        String dateFormatString1;
        String dateFormatString2;
        String dateFormatString3;
        String dateFormatString4;
        
        int f1;
        int f2;

        if (str.contains("-")) {
            d = str.split("-");
            
            dateFormatString1 = "dd-MM-yyyy";
            dateFormatString2 = "MM-dd-yyyy";
            dateFormatString3 = "yyyy-dd-MM";
            dateFormatString4 = "yyyy-MM-dd";

        } 
        else if (str.contains("\\/")) 
        {
            dateFormatString1 = "dd\\/MM\\/yyyy";
            dateFormatString2 = "MM\\/dd\\/yyyy";
            dateFormatString3 = "yyyy\\/dd\\/MM";
            dateFormatString4 = "yyyy\\/MM\\/dd";
            
            //d = str.split("\\/");
        } 
        else if (str.contains("\\")) 
        {
            
            dateFormatString1 = "dd\\MM\\yyyy";
            dateFormatString2 = "MM\\dd\\yyyy";
            dateFormatString3 = "yyyy\\dd\\MM";
            dateFormatString4 = "yyyy\\MM\\dd";
            
            //d = str.split("\\");
        } 
        else 
        {

            return null;
        }
        
        try
        {    
            DateFormat dateFormat1 = new SimpleDateFormat(dateFormatString1);
            dateFormat1.parse(str);
        }
        catch(Exception e)
        {
                    
        }

        //f1 = parseInteger(d[0]);
        //f2 = parseInteger(d[1]);

        return null;
    }

    /**
     * Parses an string into an integer.
     *
     * @param str
     * @return
     */
    public int parseInteger(String str) {
        int result = 0;
        int place = 1;

        if (str.charAt(0) == '-') {
            for (int i = 1; i <= str.length(); i++) {
                result = result + (str.charAt(i) * place);

                place = place * 10;
            }

            result = result * -1;
        } else {
            for (int i = 0; i < str.length(); i++) {
                result = result + (str.charAt(i) * place);

                place = place * 10;
            }
        }

        return result;
    }

}
