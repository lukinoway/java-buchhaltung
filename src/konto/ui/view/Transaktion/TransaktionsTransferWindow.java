package konto.ui.view.Transaktion;

import java.time.LocalDate;

import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import konto.data.container.TransaktionsContainer;
import konto.data.model.Transaktion;
import konto.ui.elements.KontoComboBox;
import konto.ui.session.SessionManager;

/**
 * This window is used for transfers between 2 kontos of the same user
 * @author lpichle
 *
 */
public class TransaktionsTransferWindow extends Window {

    private static final long serialVersionUID = 1L;
    TransaktionsContainer container;
    
    GridLayout gridView = new GridLayout(2, 3);
    //TextField transaktionsText = new TextField("Text");
    TextField transaktionsBetrag = new TextField("Betrag");
    KontoComboBox fromKonto = new KontoComboBox();
    KontoComboBox toKonto = new KontoComboBox();
    Button saveBtn = new Button("speichern");
    Button cancelBtn = new Button("abbrechen");

    public TransaktionsTransferWindow() {
	
	this.container = SessionManager.getTransaktionsContainer();
	this.setCaption("Transfer VON-ZU");
	buildGrid();
    }
    
    private void buildGrid() {
	
	gridView.addComponent(fromKonto, 0, 0, 0, 0);
	fromKonto.setCaption("Von Konto");
	gridView.addComponent(toKonto, 1, 0, 1, 0);
	toKonto.setCaption("Zu Konto");
	gridView.addComponent(transaktionsBetrag, 0, 1, 0, 1);
	
	gridView.addComponent(saveBtn, 0, 2, 0, 2);
	gridView.addComponent(cancelBtn, 1, 2, 1, 2);
	
	// add new entry
	saveBtn.addClickListener(new ClickListener() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void buttonClick(ClickEvent event) {
		addData();
	    }
	});

	// close window
	cancelBtn.addClickListener(new ClickListener() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void buttonClick(ClickEvent event) {
		TransaktionsTransferWindow.this.close();

	    }

	});
	
	gridView.setMargin(true);
	this.setContent(gridView);
	this.center();
	
    }
    
    /**
     * Function to check if the input is sane
     */
    private boolean validateInput() {
	boolean valid = false;
	try {
	    if (transaktionsBetrag == null | transaktionsBetrag.getValue() == "") {
		transaktionsBetrag.focus();
		throw new NullPointerException("TransaktionsBetrag fehlt");
	    }
	    if (!transaktionsBetrag.isEmpty()) {
		@SuppressWarnings("unused")
		double d = Double.parseDouble(transaktionsBetrag.getValue());
	    }
	    if (fromKonto == null || fromKonto.getValue() == null) {
		fromKonto.focus();
		throw new NullPointerException("fromKonto fehlt");
	    }
	    if (toKonto == null || toKonto.getValue() == null) {
		toKonto.focus();
		throw new NullPointerException("toKonto fehlt");
	    }
	    if (toKonto.getValue().equals(fromKonto.getValue())) {
		toKonto.focus();
		throw new NullPointerException("toKonto darf nicht gleich fromKonto sein!");
	    }
	    else {
		valid = true;
	    }
	} catch (NullPointerException e) {
	    System.out.println("TransferWindow - " + e);
	} catch (NumberFormatException e) {
	    // entered value is no number
	    transaktionsBetrag.focus();
	}
	return valid;
    }

    protected void addData() {
	if(validateInput()) {
	    if (container != null) {
		try {
		    // get ComboBox Values
		    int fromkontoId = fromKonto.getComboBoxIDValue();
		    int tokontoId = toKonto.getComboBoxIDValue();
		    double betrag = Double.parseDouble(transaktionsBetrag.getValue());
		    String text = "Übertrag";

		    // create new Transaktion
		    Transaktion fromTransaktion = new Transaktion(LocalDate.now(),
			    betrag, text, fromkontoId, 0);
		    
		    Transaktion toTransaktion = new Transaktion(LocalDate.now(),
			    betrag*-1, text, tokontoId, 0);

		    container.addTransaktion(fromTransaktion);
		    container.addTransaktion(toTransaktion);
		    
		    // close window
		    TransaktionsTransferWindow.this.close();
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	}
	
    }
    
}
