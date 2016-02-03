package konto.ui.view.Transaktion;

import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class NewTransaktionWindow extends Window{

    TextField transaktionsText = new TextField("Text");
    TextField transaktionsBetrag = new TextField("Betrag");
    DateField transaktionsDatum = new DateField("Datum");
    ComboBox transaktionsType = new ComboBox("Type");
    Button saveBtn = new Button("speichern");
    Button cancelBtn = new Button("abbrechen");
    
    // Layout stuff
    GridLayout gridView = new GridLayout(2, 6);
    
    public NewTransaktionWindow() {
	buildGrid();
		
	this.setContent(gridView);
	this.center();
	this.setCaption("Neue Transaktion");
	
	saveBtn.addClickListener(new Button.ClickListener() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void buttonClick(ClickEvent event) {
		validateInput();
	    }
	});

    }
    
    /**
     * Function to build grid for Window
     */
    private void buildGrid() {
	gridView.addComponent(transaktionsText, 0, 0, 1, 0);
	gridView.addComponent(transaktionsBetrag, 0, 1, 0, 1);
	gridView.addComponent(transaktionsDatum, 0, 2, 0, 2);
	gridView.addComponent(transaktionsType, 0, 3, 0, 3);
	
	gridView.addComponent(saveBtn, 0, 5, 0, 5);
	gridView.addComponent(cancelBtn, 1, 5, 1, 5);
	gridView.setMargin(true);
    }
    
    /**
     * Function to check if the input is sane
     */
    private void validateInput() {
	try {
	    if (transaktionsText == null | transaktionsText.getValue() == "") {
		transaktionsText.focus();
		throw new NullPointerException("TransaktionsText fehlt");
	    }
	    if (transaktionsBetrag == null | transaktionsBetrag.getValue() == "") {
		transaktionsBetrag.focus();
		throw new NullPointerException("TransaktionsBetrag fehlt");
	    }
	    // maybe i should set this to the current date?!
	    if (transaktionsDatum.getValue() == null) {
		transaktionsDatum.focus();
		throw new NullPointerException("TransaktionsDatum fehlt");
	    }
	    if (transaktionsType == null) {
		transaktionsType.focus();
		throw new NullPointerException("TransaktionsType fehlt");
	    }
	} catch (NullPointerException e) {
	    System.out.println("NewTransaktionWindow - " + e);
	}
    }
}
