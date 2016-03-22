package konto.ui.view.Category;

import com.vaadin.data.Item;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

import konto.data.container.CategoryContainer;
import konto.data.model.Category;
import konto.ui.session.SessionManager;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class CategoryWindow extends Window {

    private static final long serialVersionUID = 1L;

    // Layout stuff
    GridLayout gridView = new GridLayout(2, 4);
    TextField categoryName = new TextField("Bezeichnung");
    ComboBox parent = new ComboBox("Gruppe");

    Button saveBtn = new Button("speichern");
    Button cancelBtn = new Button("abbrechen");

    CategoryContainer container;
    private boolean updateData = false;
    private Category category;
    private Object itemId;

    public CategoryWindow() {

	// get container from session
	this.container = SessionManager.getCategoryContainer();
	this.setContent(gridView);
	this.center();
	this.setCaption("Neue Kategorie");

	// build grid
	buildGrid();

	categoryName.focus();
    }
    
    public CategoryWindow(Object itemId) {

	// get container from session
	this.container = SessionManager.getCategoryContainer();
	this.setContent(gridView);
	this.center();
	this.setCaption("Update Kategorie");
	this.updateData = true;
	this.itemId = itemId;

	// build grid
	buildGrid();
	loadCategory(container.getItem(itemId));

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
		    updateCategory();
		    CategoryWindow.this.close();
		}
	    }
	});

	// close window
	cancelBtn.addClickListener(new ClickListener() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void buttonClick(ClickEvent event) {
		CategoryWindow.this.close();

	    }

	});
    }
    
    
    private void loadCategory(Item item) {
	category = this.container.buildCategory(item);
	
	// set Values
	categoryName.setValue(category.getTypeText());
    }

    private void addData() {
	if (validateInput()) {
	    if (container != null) {
		try {
		    category = new Category(categoryName.getValue());

		    container.addCategory(category);
		    
		    // update stored data
		    SessionManager.getCategoryMap().put(category.getTypeId(), category.getTypeText());

		    // reset input fields
		    categoryName.setValue("");
		    categoryName.focus();

		} catch (Exception e) {

		}
	    }
	}
    }
    
    private void updateCategory() {
	if (validateInput()) {
	    if (container != null) {
		try {
		    category.setTypeText(categoryName.getValue());
		    container.updateCategory(itemId, category);
		    // update stored data
		    SessionManager.getCategoryMap().replace(category.getTypeId(), category.getTypeText());
		} catch (Exception e) {

		}
	    }
	}
    }

}
