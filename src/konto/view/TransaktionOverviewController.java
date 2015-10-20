package konto.view;


import konto.MainApp;
import konto.model.Konto;
import konto.model.Transaktion;
import konto.DBUtil.TransaktionDBUtil;

import java.io.IOException;
import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TransaktionOverviewController {

	// Ref. für GUI
	@FXML
	private DatePicker startdate;
	@FXML
	private DatePicker enddate;
	@FXML
	private ComboBox<String> typebox;
	
	// table ref
    @FXML
    public TableView<Transaktion> TransaktionsTable;
    @FXML
    public TableColumn<Transaktion, LocalDate> trDateColumn;
    @FXML
    public TableColumn<Transaktion, Number> trBetragColumn;
    @FXML
    public TableColumn<Transaktion, String> trTextColumn;
    @FXML
    public TableColumn<Transaktion, Number> trIDColumn;

    // Reference to the main application.
    private MainApp mainApp;

    
    /**
     * Constructor, allways set it to public!!
     */
    public TransaktionOverviewController() {
    }

    /**
     * Initialisiere Controller nachdem das FXML geladen wurde
     */
    @FXML
    private void initialize() {
    	// initialisiere Transaktionstabelle
    	trDateColumn.setCellValueFactory(cellDate -> cellDate.getValue().TransaktionsDateProperty());
    	trBetragColumn.setCellValueFactory(cellDate -> cellDate.getValue().TransaktionsBetragProperty());
    	trTextColumn.setCellValueFactory(cellDate -> cellDate.getValue().TransaktionsTextProperty());
    	trIDColumn.setCellValueFactory(cellDate -> cellDate.getValue().TransaktionsIdProperty());
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected transaktion.
     */
    @FXML
    private void handleShowDetail() {
        Transaktion selectedTransaktion = TransaktionsTable.getSelectionModel().getSelectedItem();
        if (selectedTransaktion != null) {
        	try {
        		mainApp.loadDetailForSelectedTransaktion(selectedTransaktion);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("Keine Transaktion ausgewählt");
            alert.setContentText("Bitte Transaktion ausgewählt.");

            alert.showAndWait();
        }
    }
    
    /**
     * Main Function to get Data from DB
     */
    @FXML
    private void loadTransaktionFromDB() {
    	try {
    		TransaktionDBUtil util = new TransaktionDBUtil();
    		util.setController(this);
    		// now perform some checking on selected values
    		if (this.startdate.getValue()!=null && this.enddate.getValue()!=null) {
    			if (typebox.getValue()!=null) {
    				util.getTransaktionFromDB(this.startdate.getValue(), this.enddate.getValue(), typebox.getValue()); 				
    			} 
    			else {
    				util.getTransaktionFromDB(this.startdate.getValue(), this.enddate.getValue());
    			}
    		} 
    		else if (typebox.getValue()!=null) {
    			util.getTransaktionFromDB(typebox.getValue());
    		}
    		else {
                // wrong input
                Alert alert = new Alert(AlertType.WARNING);
                alert.initOwner(mainApp.getPrimaryStage());
                alert.setTitle("Fehlerhafte Eingabe");
                alert.setHeaderText("Bitte Eingabe prüfen");
                alert.setContentText("Datum vorhanden? Type ausgewählt?");

                alert.showAndWait();
    		}
    		
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        TransaktionsTable.setItems(mainApp.getTransaktionData());
    }
}
