package konto.ui.view.Konto;

import com.vaadin.data.Item;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;

import konto.data.container.KontoContainer;
import konto.data.model.Konto;
import konto.data.model.LoginUser;
import konto.ui.session.SessionManager;

public class KontoWindow extends Window {

    private static final long serialVersionUID = 1L;

    LoginUser user;

    GridLayout gridView = new GridLayout(2, 8);
    TextField kontoNr = new TextField("Konto NR");
    TextField kontoText = new TextField("Beschreibung");
    TextArea transferInfo = new TextArea("Überweisungs Details");
    TextField bankURLText = new TextField("Online Banking Adresse");
    CheckBox visibleCheck = new CheckBox("Sichtbar für andere");
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
	visibleCheck.setValue(konto.isVisible());
	transferInfo.setValue(konto.getKontoTransferInfo());
	bankURLText.setValue(konto.getBankURL());
    }

    protected void addData() {
	if (validateInput()) {
	    if (container != null) {
		try {
		    konto = new Konto(kontoNr.getValue(), kontoText.getValue(), user.getUserId(), 
			    visibleCheck.getValue(), transferInfo.getValue().toString(), bankURLText.getValue());

		    container.addKonto(konto);
		    
		    // update stored data
		    SessionManager.getKontoMap().put(konto.getKontoId(), konto.getKontoName());

		    // close window
		    KontoWindow.this.close();

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
		    konto.setVisible(visibleCheck.getValue());
		    konto.setKontoTransferInfo(transferInfo.getValue());
		    konto.setBankURL(bankURLText.getValue());

		    container.updateKonto(itemId, konto);
		    
		    // update stored data
		    SessionManager.getKontoMap().replace(konto.getKontoId(), konto.getKontoName());
		    
		    KontoWindow.this.close();

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
	
	gridView.addComponent(bankURLText, 0, 2, 1, 2);
	bankURLText.setWidth(100, Unit.PERCENTAGE);
	
	gridView.addComponent(transferInfo, 0, 3, 1, 5);
	transferInfo.setWidth(100, Unit.PERCENTAGE);
	
	gridView.addComponent(visibleCheck, 0, 6, 1, 6);

	gridView.addComponent(saveBtn, 0, 7, 0, 7);
	gridView.addComponent(cancelBtn, 1, 7, 1, 7);
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
