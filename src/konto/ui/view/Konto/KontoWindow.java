package konto.ui.view.Konto;

import com.vaadin.data.Item;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import konto.data.container.KontoContainer;
import konto.data.model.Konto;
import konto.data.model.LoginUser;
import konto.ui.session.SessionManager;

public class KontoWindow extends Window {

    private static final long serialVersionUID = 1L;

    LoginUser user;

    GridLayout gridView = new GridLayout(2, 4);
    TextField kontoNr = new TextField("Konto NR");
    TextField kontoText = new TextField("Beschreibung");
    Button saveBtn = new Button("speichern");
    Button cancelBtn = new Button("abbrechen");

    KontoContainer container;
    
    private boolean updateData = false;
    private Konto konto;
    private Object itemId;

    public KontoWindow() {
	this.container = SessionManager.getKontoContainer();
	this.user = SessionManager.getUser();
	this.setContent(gridView);
	this.center();
	this.setCaption("Neues Konto");

	// build grid
	buildGrid();

    }
    
    public KontoWindow(Object itemId) {
	this.container = SessionManager.getKontoContainer();
	this.user = SessionManager.getUser();
	this.setContent(gridView);
	this.center();
	this.setCaption("Update Konto");
	this.itemId = itemId;
	this.updateData = true;

	// build grid
	buildGrid();
	loadKonto(container.getItem(itemId));

    }

    private boolean validateInput() {
	boolean valid = false;
	try {
	    if (kontoNr == null || kontoNr.getValue() == "") {
		kontoNr.focus();
		throw new NullPointerException("Konto NR fehlt");
	    }
	    if (kontoText == null || kontoText.getValue() == "") {
		kontoText.focus();
		throw new NullPointerException("Konto Beschreibung fehlt");
	    } else {
		valid = true;
	    }
	} catch (NullPointerException e) {
	    System.out.println("NewCategoryWindow - " + e);
	}

	return valid;
    }
    
    private void loadKonto(Item item) {
	konto = this.container.buildKonto(item);
	
	// set values
	kontoNr.setValue(konto.getKontoNr());
	kontoText.setValue(konto.getKontoName());
    }

    protected void addData() {
	if (validateInput()) {
	    if (container != null) {
		try {
		    konto = new Konto(kontoNr.getValue(), kontoText.getValue(), user.getUserId());

		    container.addKonto(konto);
		    
		    // update stored data
		    SessionManager.getKontoMap().put(konto.getKontoId(), konto.getKontoName());

		    kontoNr.setValue("");
		    kontoText.setValue("");
		    kontoNr.focus();

		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	}

    }
    
    private void updateKonto() {
	if (validateInput()) {
	    if (container != null) {
		try {
		    konto.setKontoNr(kontoNr.getValue());
		    konto.setKontoName(kontoText.getValue());

		    container.updateKonto(itemId, konto);
		    
		    // update stored data
		    SessionManager.getKontoMap().replace(konto.getKontoId(), konto.getKontoName());

		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	}
    }

    private void buildGrid() {
	gridView.addComponent(kontoNr, 0, 0, 1, 0);
	kontoNr.setWidth(100, Unit.PERCENTAGE);

	gridView.addComponent(kontoText, 0, 1, 1, 1);
	kontoText.setWidth(100, Unit.PERCENTAGE);

	gridView.addComponent(saveBtn, 0, 3, 0, 3);
	gridView.addComponent(cancelBtn, 1, 3, 1, 3);
	gridView.setMargin(true);
	
	saveBtn.setClickShortcut(KeyCode.ENTER);
	cancelBtn.setClickShortcut(KeyCode.ESCAPE);

	// add new entry
	saveBtn.addClickListener(new ClickListener() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void buttonClick(ClickEvent event) {
		if(!updateData) {
		    addData();
		}
		else {
		    updateKonto();
		    KontoWindow.this.close();
		}
	    }
	});

	// close window
	cancelBtn.addClickListener(new ClickListener() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void buttonClick(ClickEvent event) {
		KontoWindow.this.close();

	    }

	});

    }
}
