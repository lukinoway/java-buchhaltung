package konto.ui.view.Category;

import com.vaadin.data.Item;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.Grid;
import com.vaadin.ui.renderers.ButtonRenderer;

import konto.data.DBUtil.CategoryDBUtil;
import konto.data.DBUtil.ICategory;
import konto.data.model.Category;

public class CategoryGrid extends Grid{

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

	}
	
	
}