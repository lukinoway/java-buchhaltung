package konto.ui.view.Category;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

import konto.data.DBUtil.CategoryDBUtil;
import konto.data.DBUtil.ICategory;
import konto.data.container.CategoryContainer;
import konto.data.model.Category;
import konto.ui.session.SessionManager;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class NewCategoryWindow extends Window {

    private static final long serialVersionUID = 1L;

    // Layout stuff
    GridLayout gridView = new GridLayout(2, 4);
    TextField categoryName = new TextField("Bezeichnung");
    ComboBox parent = new ComboBox("Gruppe");

    Button saveBtn = new Button("speichern");
    Button cancelBtn = new Button("abbrechen");

    ICategory categoryUtil = new CategoryDBUtil();

    CategoryContainer container;

    public NewCategoryWindow() {

	// get container from session
	this.container = SessionManager.getCategoryContainer();
	this.setContent(gridView);
	this.center();
	this.setCaption("Neue Kategorie");

	// build grid
	buildGrid();
	
	saveBtn.setClickShortcut(KeyCode.ENTER);
	cancelBtn.setClickShortcut(KeyCode.ESCAPE);

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
		NewCategoryWindow.this.close();

	    }

	});

	categoryName.focus();
    }

    /**
     * validate Input
     * 
     * @return
     */
    private boolean validateInput() {
	boolean valid = false;
	try {
	    if (categoryName == null || categoryName.getValue() == "") {
		categoryName.focus();
		throw new NullPointerException("Kategorie Name fehlt");
	    } else {
		valid = true;
	    }
	} catch (NullPointerException e) {
	    System.out.println("NewCategoryWindow - " + e);
	}

	return valid;
    }

    /**
     * Function to build grid for Window
     */
    private void buildGrid() {
	gridView.addComponent(categoryName, 0, 0, 1, 0);
	categoryName.setWidth(100, Unit.PERCENTAGE);

	// maybe use this later
	// gridView.addComponent(parent, 0, 1, 0, 1);

	gridView.addComponent(saveBtn, 0, 3, 0, 3);
	gridView.addComponent(cancelBtn, 1, 3, 1, 3);
	gridView.setMargin(true);
    }

    public void addData() {
	if (validateInput()) {
	    if (container != null) {
		try {
		    Category category = new Category(categoryName.getValue());

		    container.addCategory(category);

		    // reset input fields
		    categoryName.setValue("");
		    categoryName.focus();

		} catch (Exception e) {

		}
	    }
	}
    }

}
