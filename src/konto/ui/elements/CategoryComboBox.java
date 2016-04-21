package konto.ui.elements;

import com.vaadin.data.util.IndexedContainer;

import konto.ui.session.SessionManager;

public class CategoryComboBox extends TemplateComboBox {

    private static final long serialVersionUID = 1L;
    private IndexedContainer tcontainer;
    
    public CategoryComboBox() {
	super();
	this.tcontainer = SessionManager.getCategoryContainer();
	this.setCaption("Kategorie");
	this.fillTypeComboBox();
    }
    
    public CategoryComboBox(IndexedContainer tcontainer) {
	super();
	this.tcontainer = tcontainer;
	this.setCaption("Kategorie");
	this.fillTypeComboBox();
    }
    
    private void fillTypeComboBox() {
	this.setContainerDataSource(tcontainer);
	this.setItemCaptionMode(ItemCaptionMode.PROPERTY);
	this.setItemCaptionPropertyId("Text");
	this.setNullSelectionAllowed(false);

    }

}
