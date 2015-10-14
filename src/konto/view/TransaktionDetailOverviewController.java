package konto.view;

import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import konto.MainApp;
import konto.model.Transaktion;
import konto.model.TransaktionDetail;

public class TransaktionDetailOverviewController {

	// create REF for GUI
	// transaktion part
    @FXML
    private Label trID;
    @FXML
    private Label trBetrag;
    @FXML
    private Label trDatum;
    @FXML
    private Label trText;
    @FXML
    private Label trHash;

    // transaktionDetail part
    @FXML
    private TableView<TransaktionDetail> TransaktionsDetailTable;
    @FXML
    private TableColumn<TransaktionDetail, LocalDate> trdCreatedColumn;
    @FXML
    private TableColumn<TransaktionDetail, Number> trdBetragColumn;
    @FXML
    private TableColumn<TransaktionDetail, String> trdTextColumn;
    @FXML
    private TableColumn<TransaktionDetail, Number> trdNRColumn;
    @FXML
    private TableColumn<TransaktionDetail, Number> trdTypeColumn;

	private Stage dialogStage;
    private Transaktion transaktion;
    private boolean okClicked = false;

    private MainApp mainApp;

    private ObservableList<TransaktionDetail> transaktionDetailData = FXCollections.observableArrayList();


    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    public ObservableList<TransaktionDetail> getTransaktionDetailData() {
        return transaktionDetailData;
    }

    /**
     * function that fills the header
     * @param selectedTransaktion
     */
	public void setTransaktion(Transaktion selectedTransaktion) {
		this.transaktion =  selectedTransaktion;

		// now fill the header
		trID.setText(String.valueOf(transaktion.getTransaktions_id()));
		trBetrag.setText(String.valueOf(transaktion.getTransaktions_betrag()));
		trText.setText(transaktion.getTransaktions_text());
		trDatum.setText(transaktion.getTransaktions_date().toString());
		trHash.setText("HIER MUSS ICH NOCH WAS MACHEN");

	}

	/**
	 * Function to fill up Detail List
	 * @param selectedTransaktion
	 */
	public void setTransaktionDetail(Transaktion selectedTransaktion) {
		this.transaktion =  selectedTransaktion;

		// lösche alte einträge
		transaktionDetailData.removeAll(this.transaktion.transaktionDetail);
		TransaktionsDetailTable.setItems(getTransaktionDetailData());

		for (int i = 0; i < this.transaktion.transaktionDetail.size(); i++) {

			transaktionDetailData.add(this.transaktion.transaktionDetail.get(i));

			trdCreatedColumn.setCellValueFactory(cellDate -> cellDate.getValue().TransaktionsDetailCreationDateProperty());
	    	trdBetragColumn.setCellValueFactory(cellDate -> cellDate.getValue().TransaktionsDetailBetragProperty());
	    	trdTextColumn.setCellValueFactory(cellDate -> cellDate.getValue().TransaktionsDetailTextProperty());
	    	trdNRColumn.setCellValueFactory(cellDate -> cellDate.getValue().TransaktionsDetailNrProperty());
	    	trdTypeColumn.setCellValueFactory(cellDate -> cellDate.getValue().TransaktionsDetailTypeProperty());

		}
    	// load data to table
    	TransaktionsDetailTable.setItems(getTransaktionDetailData());
	}

    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     */
    @FXML
    private void handlCreateDetail() {
    	try {
    		Transaktion selectedTransaktion = this.transaktion;
    	    if (selectedTransaktion != null) {
	        	boolean okClicked = mainApp.showCreateDetailDialog(selectedTransaktion);
	            if (okClicked) {
	            	setTransaktionDetail(selectedTransaktion);
	            }
	        } else {
	        }
	    } catch(NullPointerException e){
	    	System.out.println("Hier fehlt uns was - handlCreateDetail");
	    }
    }

    @FXML
    private void handleDeleteDetail() {
    	try {
    		TransaktionDetail selectedDetail = TransaktionsDetailTable.getSelectionModel().getSelectedItem();
    		Transaktion selectedTransaktion = this.transaktion;
    	    if (selectedTransaktion != null) {
    	    	selectedTransaktion.transaktionDetail.remove(selectedDetail);
    	    	setTransaktionDetail(selectedTransaktion);
	        }
	    } catch(NullPointerException e){
	    	System.out.println("Hier fehlt uns was - handlCreateDetail");
	    }
    }


    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

}
