package konto.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
    	createTransaktionDetail();
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


    /**
     * Function to create new Details
     */
    public void createTransaktionDetail() {
    	try {
	    	// prepare some stuff
	    	int count, type;
	    	double betrag;
	    	String tmptxt;
	    	count = transaktion.transaktionDetail.size() + 1;
	    	type = Integer.parseInt(trdType.getText());
	    	betrag = Double.parseDouble(trdBetrag.getText());
	    	tmptxt = trdText.getText();

	    	// now create our Detail
	    	this.transaktion.transaktionDetail.add(new TransaktionDetail(count, betrag, tmptxt, type));
    	} catch(NullPointerException e) {
    		System.out.println("kann nicht erstellt werden da etwas fehlt");
    	}
    }


}
