package konto.ui.view.Transaktion;

import java.time.LocalDate;
import java.util.ArrayList;

import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;

import konto.data.DBUtil.ITransaktion;
import konto.data.DBUtil.TransaktionDBUtil;
import konto.data.model.Transaktion;

public class TransaktionsContainer extends IndexedContainer {

    private static final long serialVersionUID = 1L;
    ITransaktion transaktionUtil = new TransaktionDBUtil();

    /**
     * Transaktion Container to store the data
     */
    @SuppressWarnings("unchecked")
    public TransaktionsContainer(ArrayList<Transaktion> transaktionList) {
	this.addContainerProperty("ID", Integer.class, null);
	this.addContainerProperty("Text", String.class, null);
	this.addContainerProperty("Betrag", Double.class, null);
	this.addContainerProperty("Datum", LocalDate.class, null);
	this.addContainerProperty("Hash", String.class, null);
	this.addContainerProperty("Kategorie", Integer.class, null);
	this.addContainerProperty("Konto", Integer.class, null);

	for (Transaktion transaktion : transaktionList) {
	    Object id = addItem();
	    Item item = getItem(id);
	    if (item != null) {
		item.getItemProperty("ID").setValue(transaktion.getTransaktionsId());
		item.getItemProperty("Text").setValue(transaktion.getTransaktionsText());
		item.getItemProperty("Betrag").setValue(transaktion.getTransaktionsBetrag());
		item.getItemProperty("Datum").setValue(transaktion.getTransaktionsDate());
		item.getItemProperty("Hash").setValue(transaktion.getTransaktionsHash());
		item.getItemProperty("Kategorie").setValue(transaktion.getTypeId());
		item.getItemProperty("Konto").setValue(transaktion.getKontoId());
	    }
	}
    }

    /**
     * user for db update
     * @param transaktion
     */
    @SuppressWarnings("unchecked")
    public void addTransaktion(Transaktion transaktion) {

	// create transaktion in DB
	transaktionUtil.createTransaktion(transaktion);

	// add to list
	Object id = addItem();
	Item item = getItem(id);
	if (item != null) {
	    item.getItemProperty("ID").setValue(transaktion.getTransaktionsId());
	    item.getItemProperty("Text").setValue(transaktion.getTransaktionsText());
	    item.getItemProperty("Betrag").setValue(transaktion.getTransaktionsBetrag());
	    item.getItemProperty("Datum").setValue(transaktion.getTransaktionsDate());
	    item.getItemProperty("Hash").setValue(transaktion.getTransaktionsHash());
	    item.getItemProperty("Kategorie").setValue(transaktion.getTypeId());
	    item.getItemProperty("Konto").setValue(transaktion.getKontoId());
	}
    }
    
    /**
     * use for local changes
     * @param transaktion
     */
    @SuppressWarnings("unchecked")
    public void addTransaktionLocal(Transaktion transaktion) {
	// add to list
	Object id = addItem();
	Item item = getItem(id);
	if (item != null) {
	    item.getItemProperty("ID").setValue(transaktion.getTransaktionsId());
	    item.getItemProperty("Text").setValue(transaktion.getTransaktionsText());
	    item.getItemProperty("Betrag").setValue(transaktion.getTransaktionsBetrag());
	    item.getItemProperty("Datum").setValue(transaktion.getTransaktionsDate());
	    item.getItemProperty("Hash").setValue(transaktion.getTransaktionsHash());
	    item.getItemProperty("Kategorie").setValue(transaktion.getTypeId());
	    item.getItemProperty("Konto").setValue(transaktion.getKontoId());
	}
    }

    /**
     * remove transaktion from DB and from List
     * @param itemId
     */
    public void removeItemDB(Object itemId) {
	Item item = getItem(itemId);
	if (item != null) {
	    // delete transaktion from DB
	    Transaktion transaktion = buildTransaktion(item);
	    transaktionUtil.deleteTransaktion(transaktion);

	    // remove from list
	    removeItem(itemId);
	    System.out.println("Delete transaktion ID: " + transaktion.getTransaktionsId());
	}
    }

    /**
     * Build transaktion from item
     * 
     * @param item
     * @return Transaktion
     */
    public Transaktion buildTransaktion(Item item) {
	return new Transaktion((Integer) item.getItemProperty("ID").getValue(),
		(LocalDate) item.getItemProperty("Datum").getValue(),
		(Double) item.getItemProperty("Betrag").getValue(), (String) item.getItemProperty("Text").getValue(),
		(String) item.getItemProperty("Hash").getValue(), (Integer) item.getItemProperty("Konto").getValue(),
		(Integer) item.getItemProperty("Kategorie").getValue());

    }
}
