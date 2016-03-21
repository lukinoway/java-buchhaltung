package konto.ui.view.Transaktion;

import java.util.HashMap;

import com.vaadin.data.Item;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.renderers.ButtonRenderer;

import konto.data.container.TransaktionsContainer;
import konto.ui.session.SessionManager;

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
	getColumns().stream().forEach(c -> c.setSortable(true));
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
	
	// add Kategory Name + Konto Name
	HashMap<Integer, String> categoryMap = SessionManager.getCategoryMap();
	wrapperContainer.addGeneratedProperty("Kategorie", new PropertyValueGenerator<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getValue(Item item, Object itemId, Object propertyId) {
		int id = (Integer)item.getItemProperty("KategorieId").getValue();
		
		Object tmp = categoryMap.get(id);
		System.out.println("Kategorie ID: " + id);
		return tmp.toString();
	    }

	    @Override
	    public Class<String> getType() {
		return String.class;
	    }
	    
	});
	
	// add Kategory Name + Konto Name
	wrapperContainer.addGeneratedProperty("Konto", new PropertyValueGenerator<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getValue(Item item, Object itemId, Object propertyId) {
		int id = (Integer)item.getItemProperty("KontoId").getValue();
		return SessionManager.getKontoMap().get(id).toString();
	    }

	    @Override
	    public Class<String> getType() {
		return String.class;
	    }
	    
	});
	
    }
    
    

}
