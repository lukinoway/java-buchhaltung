package konto.ui.view.Payment;

import java.util.HashMap;

import com.vaadin.data.Item;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.renderers.ButtonRenderer;

import konto.data.DBUtil.IKonto;
import konto.data.DBUtil.KontoDBUtil;
import konto.data.container.PaymentContainer;

public class PaymentGrid extends Grid {

    private static final long serialVersionUID = 1L;
    HashMap<Integer, String> kontoUserName = null;
    IKonto kontoUtil = new KontoDBUtil();
    
    public PaymentGrid(PaymentContainer container) {
	this.setWidth(1000, Unit.PIXELS);

	// load userNames
	kontoUserName = kontoUtil.getUserNameforVisibleKonto();
	
	// create a wrapper container
	GeneratedPropertyContainer wrapperContainer = new GeneratedPropertyContainer(container);
	wrapperContainer.removeContainerProperty("ID");
	wrapperContainer.removeContainerProperty("creatorKnt");
	wrapperContainer.removeContainerProperty("borrowerKnt");
	setContainerDataSource(wrapperContainer);
	
	// add new Properties
	wrapperContainer.addGeneratedProperty("Sender", new PropertyValueGenerator<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getValue(Item item, Object itemId, Object propertyId) {
		int id = (Integer) item.getItemProperty("borrowerKnt").getValue();
		try {
		    return kontoUserName.get(id);
		} catch (Exception e) {
		    return new String("Unbekanntes Konto");
		}
	    }

	    @Override
	    public Class<String> getType() {
		return String.class;
	    }
	    
	});
	
	wrapperContainer.addGeneratedProperty("Empfänger", new PropertyValueGenerator<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getValue(Item item, Object itemId, Object propertyId) {
		int id = (Integer) item.getItemProperty("creatorKnt").getValue();
		try {
		    return kontoUserName.get(id);
		} catch (Exception e) {
		    return new String("Unbekanntes Konto");
		}
	    }

	    @Override
	    public Class<String> getType() {
		return String.class;
	    }
	    
	});
	
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
	    //container.removeItemDB(itemId);

	});

	getColumn("delete").setRenderer(deleteBtn);
	getColumns().stream().forEach(c -> c.setSortable(false));
	
	this.addItemClickListener(new ItemClickListener() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void itemClick(ItemClickEvent event) {
		if(event.isDoubleClick()) {
		    PaymentWindow w = new PaymentWindow(event.getItemId());
		    UI.getCurrent().addWindow(w);
		    w.focus();
		}
		
	    }
	    
	});
	
	this.setColumns("Datum", "Sender", "Empfänger", "Text", "Betrag", "Status");
    }

}
