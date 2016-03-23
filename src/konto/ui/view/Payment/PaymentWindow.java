package konto.ui.view.Payment;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import konto.ui.view.Transaktion.TransaktionWindow;

public class PaymentWindow extends Window{

    private static final long serialVersionUID = 1L;
    
    TextField paymentText = new TextField("Text");
    TextField paymentBetrag = new TextField("Betrag");
    Button saveBtn = new Button("speichern");
    Button cancelBtn = new Button("abbrechen");
    
    GridLayout gridView = new GridLayout(2, 7);
    
    public PaymentWindow() {
	
	buildGrid();
	
    }
    
    private void buildGrid() {
	
	gridView.addComponent(paymentText, 0, 0, 1, 0);
	paymentText.setWidth(100, Unit.PERCENTAGE);

	gridView.addComponent(paymentBetrag, 0, 1, 0, 1);
	gridView.addComponent(transaktionsKonto, 0, 3, 1, 3);
	gridView.addComponent(transaktionsType, 0, 4, 1, 4);

	gridView.addComponent(saveBtn, 0, 6, 0, 6);
	gridView.addComponent(cancelBtn, 1, 6, 1, 6);
	gridView.setMargin(true);
	
	saveBtn.setClickShortcut(KeyCode.ENTER);
	cancelBtn.setClickShortcut(KeyCode.ESCAPE);
	
	// add new entry
	saveBtn.addClickListener(new ClickListener() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void buttonClick(ClickEvent event) {
		addPayment();
	    }
	});

	// close window
	cancelBtn.addClickListener(new ClickListener() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void buttonClick(ClickEvent event) {
		PaymentWindow.this.close();

	    }

	});
	
	this.setContent(gridView);
	this.center();
    }
    
    /**
     * Function to check if the input is sane
     */
    private boolean validateInput() {
	boolean valid = false;
	try {
	    if (paymentText == null | paymentText.getValue() == "") {
		paymentText.focus();
		throw new NullPointerException("paymentText fehlt");
	    }
	    if (paymentBetrag == null | paymentBetrag.getValue() == "") {
		paymentBetrag.focus();
		throw new NullPointerException("paymentBetrag fehlt");
	    }
	    if (!paymentBetrag.isEmpty()) {
		@SuppressWarnings("unused")
		double d = Double.parseDouble(paymentBetrag.getValue());
	    } else {
		valid = true;
	    }
	} catch (NullPointerException e) {
	    System.out.println("NewTransaktionWindow - " + e);
	} catch (NumberFormatException e) {
	    // entered value is no number
	    paymentBetrag.focus();
	}
	return valid;
    }

    protected void addPayment() {
	// TODO Auto-generated method stub
	
    }

}
