package konto.ui.view.Transaktion;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import com.vaadin.data.Item;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

import konto.data.DBUtil.CategoryDBUtil;
import konto.data.DBUtil.ICategory;
import konto.data.DBUtil.IKonto;
import konto.data.DBUtil.KontoDBUtil;
import konto.data.container.TransaktionsContainer;
import konto.data.model.LoginUser;
import konto.data.model.Transaktion;
import konto.ui.elements.KontoComboBox;
import konto.ui.elements.CategoryComboBox;
import konto.ui.session.SessionManager;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class TransaktionWindow extends Window {

    private static final long serialVersionUID = 1L;
    TextField transaktionsText = new TextField("Text");
    TextField transaktionsBetrag = new TextField("Betrag");
    DateField transaktionsDatum = new DateField("Datum");
    KontoComboBox transaktionsKonto = new KontoComboBox();
    CategoryComboBox transaktionsType = new CategoryComboBox();
    Button saveBtn = new Button("speichern");
    Button cancelBtn = new Button("abbrechen");

    TransaktionsContainer container;

    IKonto kontoUtil = new KontoDBUtil();
    ICategory categoryUtil = new CategoryDBUtil();

    LoginUser user;

    // Layout stuff
    GridLayout gridView = new GridLayout(2, 7);
    
    private boolean updateData = false;
    private Transaktion transaktion;
    private Object itemId;

    public TransaktionWindow() {

	this.container = SessionManager.getTransaktionsContainer();
	this.setCaption("Neue Transaktion");
	this.user = SessionManager.getUser();

	// build grid
	buildGrid();
	
    }
    
    public TransaktionWindow(Object itemId) {

	this.container = SessionManager.getTransaktionsContainer();
	this.setCaption("Update Transaktion");
	this.user = SessionManager.getUser();
	this.updateData = true;
	this.itemId = itemId;

	// build grid
	buildGrid();
	loadTransaktion(container.getItem(itemId));
	
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
	
	saveBtn.setClickShortcut(KeyCode.ENTER);
	cancelBtn.setClickShortcut(KeyCode.ESCAPE);
	
	// add new entry
	saveBtn.addClickListener(new ClickListener() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void buttonClick(ClickEvent event) {
		if (!updateData) {
		    addData();
		}
		else {
		    updateTransaktion();
		    TransaktionWindow.this.close();
		}
	    }
	});

	// close window
	cancelBtn.addClickListener(new ClickListener() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void buttonClick(ClickEvent event) {
		TransaktionWindow.this.close();

	    }

	});
	
	this.setContent(gridView);
	this.center();
    }
    

    /**
     * load transaktion from ID
     * @param indexId
     */
    private void loadTransaktion(Item item) {
	transaktion = this.container.buildTransaktion(item);
	
	// set Values
	transaktionsText.setValue(transaktion.getTransaktionsText());
	transaktionsBetrag.setValue(String.valueOf(transaktion.getTransaktionsBetrag()));
	transaktionsDatum.setValue(Date.valueOf(transaktion.getTransaktionsDate()));
	transaktionsKonto.setComboBoxValue(transaktion.getKontoId());
	transaktionsType.setComboBoxValue(transaktion.getTypeId());
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
	    try {
		System.out.println("Datum: '" + transaktionsDatum.getValue() + "'");
		returnDate = LocalDateTime.ofInstant(transaktionsDatum.getValue().toInstant(), ZoneId.systemDefault())
			.toLocalDate();
	    } catch (Exception e) {
		returnDate = convertDateToLocalDate((Date) transaktionsDatum.getValue());
	    }
	}
	return returnDate;
    }
    
    private LocalDate convertDateToLocalDate(Date date) {
	// date conversion
	Instant instant = Instant.ofEpochMilli(date.getTime());
	return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
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
		    transaktion = new Transaktion(getTransaktionsDate(),
			    Double.parseDouble(transaktionsBetrag.getValue()), transaktionsText.getValue(), kontoId,
			    typeId);

		    container.addTransaktion(transaktion);
		    
		    // reset window
		    transaktionsText.setValue("");
		    transaktionsBetrag.setValue("");
		    transaktionsDatum.setValue(null);
		    transaktionsKonto.setValue(null);
		    transaktionsType.setValue(null);
		    
		    transaktionsText.focus();

		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	}
    }
    
    protected void updateTransaktion() {
	if (validateInput()) {
	    if (container != null) {
		try {
		    // get ComboBox Values
		    int kontoId = transaktionsKonto.getComboBoxIDValue();
		    int typeId = transaktionsType.getComboBoxIDValue();
		    
		    transaktion.setKontoId(kontoId);
		    transaktion.setTypeId(typeId);
		    transaktion.setTransaktionsBetrag(Double.parseDouble(transaktionsBetrag.getValue()));
		    transaktion.setTransaktionsText(transaktionsText.getValue());
		    transaktion.setTransaktionsDate(getTransaktionsDate());
		    transaktion.createTransaktionsHash();
		    
		    container.updateTransaktion(itemId, transaktion);
		    
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	}
	
    }

}
