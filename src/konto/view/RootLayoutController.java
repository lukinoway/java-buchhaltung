package konto.view;

import konto.MainApp;
import konto.DBUtil.RechnungsDBUtil;
import konto.model.Konto;
import konto.model.Transaktion;
//import konto.view.*;

import java.io.File;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;

/**
 * The controller for the root layout. The root layout provides the basic
 * application layout containing a menu bar and space where other JavaFX
 * elements can be placed.
 */

public class RootLayoutController {

    // Reference to the main application.
    private MainApp mainApp;

    /**
     * Constructor vor der initialisierung
     */
    public RootLayoutController() {
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        //TransaktionsTable.setItems(mainApp.getTransaktionData());
    }

    /**
     * Function to load CSV File
     */
    @FXML
    public void loadCSVFile() {
    	FileChooser fileChooser = new FileChooser();
    	FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV Files (*.csv)", "*.csv");
    	fileChooser.getExtensionFilters().add(extFilter);

    	//Show file dialog
    	File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            try {
				mainApp.loadDataFromFile(file);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("ausgewähltes File: " + file.toString());
			}
        }
    }
    

    /**
     * Opens an about dialog.
     */
    @FXML
    private void handleAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("KontoAPP");
        alert.setHeaderText("About");
        alert.setContentText("Author: Lukas Pichler");

        alert.showAndWait();
    }

    @FXML
    private void handleClose() {
    	try {
    		// hier muss ich noch was machen
			//mainApp.stop();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }


}
