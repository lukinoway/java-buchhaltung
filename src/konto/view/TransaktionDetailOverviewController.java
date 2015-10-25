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
import konto.DBUtil.TransaktionDetailDBUtil;
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
	public TableView<TransaktionDetail> TransaktionsDetailTable;
    @FXML
    public TableColumn<TransaktionDetail, LocalDate> trdCreatedColumn;
    @FXML
	public TableColumn<TransaktionDetail, Number> trdBetragColumn;
    @FXML
    public TableColumn<TransaktionDetail, String> trdTextColumn;
    @FXML
    public TableColumn<TransaktionDetail, Number> trdNRColumn;
    @FXML
    public TableColumn<TransaktionDetail, Number> trdTypeColumn;

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
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
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
		trID.setText(String.valueOf(this.transaktion.getTransaktions_id()));
		trBetrag.setText(String.valueOf(this.transaktion.getTransaktions_betrag()));
		trText.setText(this.transaktion.getTransaktions_text());
		trDatum.setText(this.transaktion.getTransaktions_date().toString());
		trHash.setText(this.transaktion.getTransaktions_hash());
	}

	/**
	 * Function to fill up Detail List
	 * @param selectedTransaktion
	 */
	public void setTransaktionDetail(Transaktion selectedTransaktion) {
		try {
			this.transaktion =  selectedTransaktion;
	
			TransaktionDetailDBUtil util = new TransaktionDetailDBUtil();
			util.setController(this);
			util.getTransaktionDetailFromDB(selectedTransaktion.getTransaktions_id());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("es konnten keine Details geladen werden");
		}
	}

    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     */
    @FXML
    private void handleCreateDetail() {
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
	    	System.out.println("Hier fehlt uns was - handleCreateDetail");
	    }
    }
    
    @FXML
    private void handleUpdateDetail() {
    	try {
		TransaktionDetail selectedDetail = TransaktionsDetailTable.getSelectionModel().getSelectedItem();
		Transaktion selectedTransaktion = this.transaktion;
    	    if (selectedDetail != null) {
	        	boolean okClicked = mainApp.showUpdateDetailDialog(selectedDetail);
	            if (okClicked) {
	            	setTransaktionDetail(selectedTransaktion);
	            }
	        } else {
	        }
	    } catch(NullPointerException e){
	    	System.out.println("Hier fehlt uns was - handleUpdateDetail");
	    }
    }

    /**
     * This function will delete the selected Detail
     * @throws Exception
     */
    @FXML
    private void handleDeleteDetail() throws Exception {
    	try {
    		TransaktionDetail selectedDetail = TransaktionsDetailTable.getSelectionModel().getSelectedItem();
    		Transaktion selectedTransaktion = this.transaktion;
    		
    	    if (selectedDetail != null) {
    	    	TransaktionDetailDBUtil util = new TransaktionDetailDBUtil();
    	    	util.deleteTransaktionDetail(selectedDetail.getTransaktionsDetail_id());
    	    	setTransaktionDetail(selectedTransaktion);
	        }
	    } catch(NullPointerException e){
	    	System.out.println("Hier fehlt uns was - handlDeleteDetail");
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
