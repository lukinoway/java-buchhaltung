package konto.ui.view.Transaktion;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

import konto.data.DBUtil.CategoryDBUtil;
import konto.data.DBUtil.ICategory;
import konto.data.DBUtil.IKonto;
import konto.data.DBUtil.KontoDBUtil;
import konto.data.model.LoginUser;
import konto.data.model.Transaktion;
import konto.ui.elements.KontoComboBox;
import konto.ui.elements.TypeComboBox;
import konto.ui.session.SessionManager;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class NewTransaktionWindow extends Window {

    private static final long serialVersionUID = 1L;
    TextField transaktionsText = new TextField("Text");
    TextField transaktionsBetrag = new TextField("Betrag");
    DateField transaktionsDatum = new DateField("Datum");
    KontoComboBox transaktionsKonto = new KontoComboBox();
    TypeComboBox transaktionsType = new TypeComboBox();
    Button saveBtn = new Button("speichern");
    Button cancelBtn = new Button("abbrechen");

    TransaktionsContainer container;

    IKonto kontoUtil = new KontoDBUtil();
    ICategory categoryUtil = new CategoryDBUtil();

    LoginUser user;

    // Layout stuff
    GridLayout gridView = new GridLayout(2, 7);

    public NewTransaktionWindow(TransaktionsContainer container) {

	this.container = container;
	this.setContent(gridView);
	this.center();
	this.setCaption("Neue Transaktion");
	this.user = SessionManager.getUser();

	// build grid
	buildGrid();

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
		NewTransaktionWindow.this.close();

	    }

	});

	// add data on enter
	saveBtn.addShortcutListener(new ShortcutListener("enter Transaktion", ShortcutAction.KeyCode.ENTER, null) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void handleAction(Object sender, Object target) {
		addData();
	    }
	});

	// close window on ESC
	this.addShortcutListener(new ShortcutListener("ESC Transaktion", ShortcutAction.KeyCode.ESCAPE, null) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void handleAction(Object sender, Object target) {
		NewTransaktionWindow.this.close();
	    }
	});

    }

    /**
     * Function to build grid for Window
     */
    private void buildGrid() {
	gridView.addComponent(transaktionsText, 0, 0, 1, 0);
	transaktionsText.setWidth(100, Unit.PERCENTAGE);

	gridView.addComponent(transaktionsBetrag, 0, 1, 0, 1);
	gridView.addComponent(transaktionsDatum, 0, 2, 0, 2);
	gridView.addComponent(transaktionsKonto, 0, 3, 1, 3);
	gridView.addComponent(transaktionsType, 0, 4, 1, 4);

	gridView.addComponent(saveBtn, 0, 6, 0, 6);
	gridView.addComponent(cancelBtn, 1, 6, 1, 6);
	gridView.setMargin(true);
    }

    /**
     * Function to check if the input is sane
     */
    private boolean validateInput() {
	boolean valid = false;
	try {
	    if (transaktionsText == null | transaktionsText.getValue() == "") {
		transaktionsText.focus();
		throw new NullPointerException("TransaktionsText fehlt");
	    }
	    if (transaktionsBetrag == null | transaktionsBetrag.getValue() == "") {
		transaktionsBetrag.focus();
		throw new NullPointerException("TransaktionsBetrag fehlt");
	    }
	    if (!transaktionsBetrag.isEmpty()) {
		@SuppressWarnings("unused")
		double d = Double.parseDouble(transaktionsBetrag.getValue());
	    }
	    // maybe i should set this to the current date?!
	    if (transaktionsDatum.getValue() == null) {
		transaktionsDatum.focus();
		throw new NullPointerException("TransaktionsDatum fehlt");
	    }
	    if (transaktionsKonto == null || transaktionsKonto.getValue() == null) {
		transaktionsKonto.focus();
		throw new NullPointerException("TransaktionsKonto fehlt");
	    }
	    if (transaktionsType == null || transaktionsType.getValue() == null) {
		transaktionsType.focus();
		throw new NullPointerException("TransaktionsType fehlt");
	    } else {
		valid = true;
	    }
	} catch (NullPointerException e) {
	    System.out.println("NewTransaktionWindow - " + e);
	} catch (NumberFormatException e) {
	    // entered value is no number
	    transaktionsBetrag.focus();
	}
	return valid;
    }

    public LocalDate getTransaktionsDate() {
	LocalDate returnDate;
	if (transaktionsDatum.getValue() == null) {
	    returnDate = LocalDate.now();
	} else {
	    returnDate = LocalDateTime.ofInstant(transaktionsDatum.getValue().toInstant(), ZoneId.systemDefault())
		    .toLocalDate();
	}
	return returnDate;
    }

    /**
     * Add data to datacontainer
     */
    private void addData() {
	if (validateInput()) {
	    if (container != null) {
		try {
		    // get ComboBox Values
		    int kontoId = transaktionsKonto.getComboBoxIDValue();
		    int typeId = transaktionsType.getComboBoxIDValue();

		    // create new Transaktion
		    Transaktion transaktion = new Transaktion(getTransaktionsDate(),
			    Double.parseDouble(transaktionsBetrag.getValue()), transaktionsText.getValue(), kontoId,
			    typeId);

		    container.addTransaktion(transaktion);

		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	}
    }

}
