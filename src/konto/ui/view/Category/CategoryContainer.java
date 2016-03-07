package konto.ui.view.Category;

import java.util.ArrayList;

import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;

import konto.data.DBUtil.CategoryDBUtil;
import konto.data.DBUtil.ICategory;
import konto.data.model.Category;

public class CategoryContainer extends IndexedContainer {

    private static final long serialVersionUID = 1L;
    ICategory categoryUtil = new CategoryDBUtil();

    /**
     * Category data to store data
     * 
     * @param categoryList
     */
    @SuppressWarnings("unchecked")
    public CategoryContainer(ArrayList<Category> categoryList) {
	this.addContainerProperty("ID", Integer.class, null);
	this.addContainerProperty("Text", String.class, null);
	this.addContainerProperty("Parent", Integer.class, null);

	for (Category category : categoryList) {
	    Object id = addItem();
	    Item item = getItem(id);
	    if (item != null) {
		item.getItemProperty("ID").setValue(category.getTypeId());
		item.getItemProperty("Text").setValue(category.getTypeText());
	    }
	}
    }

    @SuppressWarnings("unchecked")
    public void addCategory(Category category) {

	// create category in DB
	categoryUtil.createCategory(category);

	// add to list
	Object id = addItem();
	Item item = getItem(id);
	if (item != null) {
	    item.getItemProperty("ID").setValue(category.getTypeId());
	    item.getItemProperty("Text").setValue(category.getTypeText());
	}

    }

    /**
     * Remove Category from DB and from List
     * @param itemId
     */
    public void removeItemDB(Object itemId) {
	Item item = getItem(itemId);
	if (item != null) {
	    Category category = buildCategory(item);
	    // delete from DB
	    categoryUtil.deleteCategory(category);

	    // now delete from List
	    removeItem(itemId);

	    System.out.println("delete Kategorie: " + category.getTypeText());
	}

    }

    /**
     * build category from item
     * @param item
     * @return Category
     */
    private Category buildCategory(Item item) {
	return new Category((Integer) item.getItemProperty("ID").getValue(),
		(String) item.getItemProperty("Text").getValue());
    }
}
