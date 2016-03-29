package konto.data.container;

import java.util.ArrayList;

import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;

import konto.data.DBUtil.IKonto;
import konto.data.DBUtil.KontoDBUtil;
import konto.data.model.Konto;
import konto.ui.session.SessionManager;

public class KontoContainer extends IndexedContainer {

    private static final long serialVersionUID = 1L;
    IKonto kontoUtil = new KontoDBUtil();

    /**
     * Category data to store data
     * 
     * @param categoryList
     */
    @SuppressWarnings("unchecked")
    public KontoContainer(ArrayList<Konto> kontoList) {
	this.addContainerProperty("ID", Integer.class, null);
	this.addContainerProperty("Konto", String.class, null);
	this.addContainerProperty("Beschreibung", String.class, null);
	this.addContainerProperty("UserId", Integer.class, null);
	this.addContainerProperty("Visible", Boolean.class, null);

	for (Konto konto : kontoList) {
	    Object id = addItem();
	    Item item = getItem(id);
	    if (item != null) {
		item.getItemProperty("ID").setValue(konto.getKontoId());
		item.getItemProperty("Konto").setValue(konto.getKontoNr());
		item.getItemProperty("Beschreibung").setValue(konto.getKontoName());
		item.getItemProperty("UserId").setValue(konto.getUserId());
		item.getItemProperty("Visible").setValue(konto.isVisible());
	    }
	}
    }

    @SuppressWarnings("unchecked")
    public void addKonto(Konto konto) {

	// create new Konto in DB
	kontoUtil.createKonto(konto);

	// add to List
	Object id = addItem();
	Item item = getItem(id);
	if (item != null) {
	    item.getItemProperty("ID").setValue(konto.getKontoId());
	    item.getItemProperty("Konto").setValue(konto.getKontoNr());
	    item.getItemProperty("Beschreibung").setValue(konto.getKontoName());
	    item.getItemProperty("UserId").setValue(konto.getUserId());
	    item.getItemProperty("Visible").setValue(konto.isVisible());
	}

    }
    
    @SuppressWarnings("unchecked")
    public void updateKonto(Object itemId, Konto konto) {
	Item item = getItem(itemId);
	if (item != null) {
	    kontoUtil.updateKonto(konto);
	    
	    item.getItemProperty("Konto").setValue(konto.getKontoNr());
	    item.getItemProperty("Beschreibung").setValue(konto.getKontoName());
	    item.getItemProperty("Visible").setValue(konto.isVisible());
	}
    }

    public void removeItemDB(Object itemId) {
	Item item = getItem(itemId);
	if (item != null) {
	    Konto konto = buildKonto(item);

	    // delete from DB
	    kontoUtil.deleteKonto(konto);
	    
	    // delete from HashMap
	    SessionManager.getKontoMap().remove(konto.getKontoId());

	    // now delete from List
	    removeItem(itemId);
	    System.out.println("delete Konto: " + konto.getKontoNr());
	}

    }
    
    public Konto buildKonto(Item item) {
	return new Konto((Integer) item.getItemProperty("ID").getValue(), 
		(String) item.getItemProperty("Konto").getValue(), 
		(String) item.getItemProperty("Beschreibung").getValue(), 
		(Integer) item.getItemProperty("UserId").getValue(),
		(boolean) item.getItemProperty("Visible").getValue(),
		kontoUtil.getTransferInformationforKonto((Integer) item.getItemProperty("ID").getValue()),
		kontoUtil.getBankURL((Integer) item.getItemProperty("ID").getValue()));
    }

}
