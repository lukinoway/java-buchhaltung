package konto.ui.view.Konto;

import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
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

public class NewKontoWindow extends Window {

    private static final long serialVersionUID = 1L;

    LoginUser user;

    GridLayout gridView = new GridLayout(2, 4);
    TextField kontoNr = new TextField("Konto NR");
    TextField kontoText = new TextField("Beschreibung");
    Button saveBtn = new Button("speichern");
    Button cancelBtn = new Button("abbrechen");

    KontoContainer container;

    public NewKontoWindow(KontoContainer container) {
	this.container = container;
	this.user = SessionManager.getUser();
	this.setContent(gridView);
	this.center();
	this.setCaption("Neues Konto");

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
		NewKontoWindow.this.close();

	    }

	});

	// add data on enter
	saveBtn.addShortcutListener(new ShortcutListener("enter Konto", ShortcutAction.KeyCode.ENTER, null) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void handleAction(Object sender, Object target) {
		addData();
	    }
	});

	// close window on ESC
	this.addShortcutListener(new ShortcutListener("ESC Konto", ShortcutAction.KeyCode.ESCAPE, null) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void handleAction(Object sender, Object target) {
		NewKontoWindow.this.close();
	    }
	});

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

    protected void addData() {
	if (validateInput()) {
	    if (container != null) {
		try {
		    Konto konto = new Konto(kontoNr.getValue(), kontoText.getValue(), user.getUserId());

		    container.addKonto(konto);

		    kontoNr.setValue("");
		    kontoText.setValue("");
		    kontoNr.focus();

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

    }
}
