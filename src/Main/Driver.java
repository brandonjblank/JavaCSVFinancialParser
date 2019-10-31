package Main;



import Main.Utilities;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.*;
//import src.CSVParser;
//import src.Utilities;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.application.Platform;

/**
 * Driver class for the CSV parser. Starts up the application as a JavaFX app.
 *
 * @author Brandon Blank
 * @version 0.01
 */
public class Driver extends Application
{

    /**
     * @param  stage the primary stage for this application.
     */
    @Override
    public void start(Stage stage)
    {
        Utilities u = new Utilities();
        boolean status = false;
        File f = u.getFile("Select a csv file", "CSV Files", "*.csv"); 

        if (f == null)
        {
            u.createAlert("Process stopped", "The process was stopped prematurely. Please try again.", AlertType.WARNING);
        }
        else
        {

            if (f.exists())
            {
                CSVParser cp = new CSVParser(f);

                status = cp.readFileAndOutputToFile();
            }

            if (status)
            {
                u.createAlert("Success!", "The File was successfully parsed!", AlertType.INFORMATION);    
            }
            else
            {
                u.createAlert("Failure!", "An error has occurred. The File could not be parsed.", AlertType.ERROR);
            }

        }
        Platform.exit();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
