package konto.view;

import java.time.LocalDate;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Table;
import com.vaadin.ui.renderers.ButtonRenderer;

import konto.model.Transaktion;

public class TransaktionsGrid extends Grid {
	
	/**
	 * this time i will try to use a grid
	 */
	public TransaktionsGrid(Container.Indexed indexed) {
		// create a wrapper container
        GeneratedPropertyContainer wrapperContainer = new GeneratedPropertyContainer(indexed);
        wrapperContainer.removeContainerProperty("id");
        setContainerDataSource(wrapperContainer);
        wrapperContainer.addGeneratedProperty("delete", new PropertyValueGenerator<String>() {
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
        getColumn("delete").setRenderer(new ButtonRenderer(event -> {
        	Object itemId = event.getItemId();
        	indexed.removeItem(itemId);
        }));
		getColumns().stream().forEach(c -> c.setSortable(false));
	}

}
