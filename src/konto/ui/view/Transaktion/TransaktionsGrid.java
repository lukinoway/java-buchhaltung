package konto.ui.view.Transaktion;

import java.util.Collection;
import java.util.HashMap;

import com.vaadin.data.Item;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;

import konto.data.container.TransaktionsContainer;
import konto.ui.session.SessionManager;

public class TransaktionsGrid extends Grid {

    private static final long serialVersionUID = 1L;
    private HashMap<Integer, String> kontoMap = SessionManager.getKontoMap();
    private HashMap<Integer, String> categoryMap = SessionManager.getCategoryMap();

    public TransaktionsGrid(TransaktionsContainer indexed) {
	
	// add style
	this.addStyleName(ValoTheme.TABLE_BORDERLESS);
	this.addStyleName("transaktionsGrid");
	this.setWidth(1000, Unit.PIXELS);
	
	// create a wrapper container
	GeneratedPropertyContainer wrapperContainer = new GeneratedPropertyContainer(indexed);
	// don't show ID values
	wrapperContainer.removeContainerProperty("ID");
	wrapperContainer.removeContainerProperty("Hash");
	wrapperContainer.removeContainerProperty("KategorieId");
	wrapperContainer.removeContainerProperty("KontoId");
	getColumns().stream().forEach(c -> c.setSortable(true));
	setContainerDataSource(wrapperContainer);
	
	// add Kategory Name
	wrapperContainer.addGeneratedProperty("Kategorie", new PropertyValueGenerator<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getValue(Item item, Object itemId, Object propertyId) {
		int id = (Integer)item.getItemProperty("KategorieId").getValue();
		try {
		    return categoryMap.get(id).toString();
		} catch (NullPointerException e) {
		    return new String("Kategorie NA");
		}
	    }

	    @Override
	    public Class<String> getType() {
		return String.class;
	    }
	    
	});
	
	// add Konto Name
	wrapperContainer.addGeneratedProperty("Konto", new PropertyValueGenerator<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getValue(Item item, Object itemId, Object propertyId) {
		int id = (Integer)item.getItemProperty("KontoId").getValue();
		try {
		    return kontoMap.get(id).toString();
		} catch (NullPointerException e) {
		    return new String("Konto NA");
		}
	    }

	    @Override
	    public Class<String> getType() {
		return String.class;
	    }
	    
	});

	// add delete column
	wrapperContainer.addGeneratedProperty("delete", new PropertyValueGenerator<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getValue(Item item, Object itemId, Object propertyID) {
		return "x";
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
	getColumn("delete").setWidth(60);
	
	this.addItemClickListener(new ItemClickListener() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void itemClick(ItemClickEvent event) {
		if(event.isDoubleClick()) {
		    TransaktionWindow w = new TransaktionWindow(event.getItemId());
		    UI.getCurrent().addWindow(w);
		    w.focus();
		}
		
	    }
	    
	});
	
	this.setColumns("Datum", "Text", "Konto", "Kategorie", "Betrag", "delete");
	
    } 
}
