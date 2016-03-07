package konto.ui.elements;

import com.vaadin.ui.Select;

import konto.data.DBUtil.CategoryDBUtil;
import konto.data.DBUtil.ICategory;
import konto.ui.view.Category.CategoryContainer;

public class TypeComboBox extends TemplateComboBox {

    private static final long serialVersionUID = 1L;
    ICategory categoryUtil = new CategoryDBUtil();
    
    public TypeComboBox() {
	super();
	this.setCaption("Kategorie");
	this.fillTypeComboBox();
    }
    
    private void fillTypeComboBox() {
	CategoryContainer kcontainer = categoryUtil.getAllCategories();

	this.setContainerDataSource(kcontainer);
	this.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
	this.setItemCaptionPropertyId("Text");
	this.setNullSelectionAllowed(false);

    }

}
