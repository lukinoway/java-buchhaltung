package konto.ui.view.Category;

import com.vaadin.data.Item;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.renderers.ButtonRenderer;

import konto.data.container.CategoryContainer;

public class CategoryGrid extends Grid {

    private static final long serialVersionUID = 1L;

    public CategoryGrid(CategoryContainer container) {
	this.setHeightMode(HeightMode.ROW);

	// create a wrapper container
	GeneratedPropertyContainer wrapperContainer = new GeneratedPropertyContainer(container);
	wrapperContainer.removeContainerProperty("ID");
	wrapperContainer.removeContainerProperty("Parent");
	setContainerDataSource(wrapperContainer);
	wrapperContainer.addGeneratedProperty("delete", new PropertyValueGenerator<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getValue(Item item, Object itemId, Object propertyID) {
		return "Delete";
	    }

	    @Override
	    public Class<String> getType() {
		return String.class;
	    }
	});
	// add renderer for delete column
	ButtonRenderer deleteBtn = new ButtonRenderer(event -> {
	    Object itemId = event.getItemId();
	    container.removeItemDB(itemId);

	});

	getColumn("delete").setRenderer(deleteBtn);
	getColumns().stream().forEach(c -> c.setSortable(false));
	
	this.addItemClickListener(new ItemClickListener() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void itemClick(ItemClickEvent event) {
		if(event.isDoubleClick()) {
		    CategoryWindow w = new CategoryWindow(event.getItemId());
		    UI.getCurrent().addWindow(w);
		    w.focus();
		}
		
	    }
	    
	});
    }

}
