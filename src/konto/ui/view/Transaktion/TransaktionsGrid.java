package konto.ui.view.Transaktion;

import com.vaadin.data.Item;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.Grid;
import com.vaadin.ui.renderers.ButtonRenderer;

import konto.data.container.TransaktionsContainer;

public class TransaktionsGrid extends Grid {

    private static final long serialVersionUID = 1L;

    public TransaktionsGrid(TransaktionsContainer indexed) {
	
	// add style
	this.addStyleName("transaktionsGrid");
	this.setWidth(1000, Unit.PIXELS);
	
	this.setHeightMode(HeightMode.ROW);
	
	//this.setHeightByRows(20);
	
	
	// create a wrapper container
	GeneratedPropertyContainer wrapperContainer = new GeneratedPropertyContainer(indexed);
	wrapperContainer.removeContainerProperty("ID");
	wrapperContainer.removeContainerProperty("Hash");
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
	    indexed.removeItemDB(itemId);
	});
	
		
	getColumn("delete").setRenderer(deleteBtn);
	getColumns().stream().forEach(c -> c.setSortable(false));
	
	
    }

}
