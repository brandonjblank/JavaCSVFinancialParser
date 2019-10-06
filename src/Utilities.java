import java.io.*;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
/**
 * Contains functions for interacting with JavaFX FileChooser and Alert functions.
 *
 * @author Brandon Blank
 * @version 0.01
 */
public class Utilities
{

    /**
     * Return null if the FileChooser is exited or the process is cancelled before selecting a file.
     * Returns a File object if a file is selected.
     * 
     * Currently this method does not support multiple extension filters.
     * For more information: https://stackoverflow.com/questions/25189123/javafx-filechooser-extensionfilter-not-working-properly-highlighted-files
     */
    public File getFile(String title, String extensionName, String extension)
    {
        File f;
        Stage s = new Stage();
       
        s.setAlwaysOnTop(true);
        s.toFront();
        FileChooser fChooser;

        try
        {
            fChooser = new FileChooser();
            fChooser.setTitle(title);
            fChooser.getExtensionFilters().addAll(
                new ExtensionFilter(extensionName, extension));
            f = fChooser.showOpenDialog(s);

            fChooser = null;
            s.close();

            return f;
        }
        catch (Exception e)
        {
            fChooser = null;
            s.close();
            return null;
            //Log issue
        }

    }

    /**
     * Sets up all required functions to generate an alert by taking arguments.
     */
    public void createAlert(String strTtitle, String strContent, AlertType aType)
    {
        Alert a =  new Alert(aType);
        a.setTitle(strTtitle);
        a.setContentText(strContent);

        a.showAndWait();

        a = null;

        
    }
}
