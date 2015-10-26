package konto.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import konto.DBUtil.TransaktionDetailDBUtil;
import konto.model.Transaktion;
import konto.model.TransaktionDetail;

public class TransaktionDetailUtilController {

	@FXML
	private TextField trdText;
	@FXML
	private TextField trdBetrag;
	@FXML
	private TextField trdType;

	private Stage dialogStage;
	private Transaktion transaktion;
	private TransaktionDetail detail;
	private boolean okClicked = false;

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

    @FXML
    private void handleOk() {
	if (this.detail != null) {
    		updateTransaktionDetail();
	} else {
		createTransaktionDetail();
	}
    	okClicked = true;
        dialogStage.close();
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

    public void loadTransaktion(Transaktion selectedTransaktion) {
    	this.transaktion = selectedTransaktion;
    }

    public void loadDetail(TransaktionDetail selectedDetail) {
	this.detail = selectedDetail;	
	this.trdText.setText(selectedDetail.getTransaktionsDetail_text());
	this.trdBetrag.setText(String.valueOf(selectedDetail.getTransaktionsDetail_betrag()));
    }


    /**
     * Function to create new Details
     */
    public void createTransaktionDetail() {
    	try {
	    	// prepare some stuff
    		TransaktionDetailDBUtil util = new TransaktionDetailDBUtil();
	    	int tr_id = this.transaktion.getTransaktions_id();
	    	double betrag;
	    	String tmptxt;
	    	betrag = Double.parseDouble(trdBetrag.getText());
	    	tmptxt = trdText.getText();

	    	// create new Detail
	    	util.insertTransaktionDetail(tr_id, tmptxt, betrag);
	    	
    	} catch(NullPointerException e) {
    		System.out.println("kann nicht erstellt werden da etwas fehlt");
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void updateTransaktionDetail() {
    	try {
    		// do some preperation
    		TransaktionDetailDBUtil util = new TransaktionDetailDBUtil();
    		String tmptxt = trdText.getText();;
    		double betrag = Double.parseDouble(trdBetrag.getText());		

    		// now update
    		util.updateTransaktionDetail(detail.getTransaktionsDetail_id(), tmptxt, betrag);
    	} catch (Exception e) {
    		System.out.println("updateTransaktionDetail - hier lief was schief");
    	}
    }


}
