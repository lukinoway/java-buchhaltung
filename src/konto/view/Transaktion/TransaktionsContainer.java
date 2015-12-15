package konto.view.Transaktion;

import java.time.LocalDate;
import java.util.ArrayList;

import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;

import konto.model.Transaktion;

public class TransaktionsContainer extends IndexedContainer {

    private static final long serialVersionUID = 1L;

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

	for (Transaktion transaktion : transaktionList) {
	    Object id = addItem();
	    Item item = getItem(id);
	    if (item != null) {
		item.getItemProperty("ID").setValue(transaktion.getTransaktionsId());
		item.getItemProperty("Text").setValue(transaktion.getTransaktionsText());
		item.getItemProperty("Betrag").setValue(transaktion.getTransaktionsBetrag());
		item.getItemProperty("Datum").setValue(transaktion.getTransaktionsDate());
		item.getItemProperty("Hash").setValue(transaktion.getTransaktionsHash());
	    }
	}
    }
}
