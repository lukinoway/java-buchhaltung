package konto.ui.elements;

import com.vaadin.ui.Select;

import konto.data.container.CategoryContainer;
import konto.ui.session.SessionManager;

public class CategoryComboBox extends TemplateComboBox {

    private static final long serialVersionUID = 1L;
    
    public CategoryComboBox() {
	super();
	this.setCaption("Kategorie");
	this.fillTypeComboBox();
    }
    
    private void fillTypeComboBox() {
	CategoryContainer kcontainer = SessionManager.getCategoryContainer();

	this.setContainerDataSource(kcontainer);
	this.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
	this.setItemCaptionPropertyId("Text");
	this.setNullSelectionAllowed(false);

    }

}
