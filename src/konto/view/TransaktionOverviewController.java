package konto.view;


import konto.MainApp;
import konto.model.Konto;
import konto.model.Transaktion;
import konto.view.*;

import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TransaktionOverviewController {

	// Ref. für GUI
    @FXML
    private TableView<Transaktion> TransaktionsTable;
    @FXML
    private TableColumn<Transaktion, LocalDate> trDateColumn;
    @FXML
    private TableColumn<Transaktion, Number> trBetragColumn;
    @FXML
    private TableColumn<Transaktion, String> trTextColumn;
    @FXML
    private TableColumn<Transaktion, Number> trIDColumn;

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
        	mainApp.loadDetailForSelectedTransaktion(selectedTransaktion);

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
