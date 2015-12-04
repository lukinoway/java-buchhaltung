package konto.view;

import java.time.LocalDate;

import com.vaadin.data.util.IndexedContainer;

public class TransaktionsContainer extends IndexedContainer {

	/**
	 * Basic Constructor
	 */
	public void TransaktionsContainer() {
		this.addContainerProperty("ID", Integer.class, null);
		this.addContainerProperty("Text", String.class, null);
		this.addContainerProperty("Betrag", Double.class, null);
		this.addContainerProperty("Datum", LocalDate.class, null);
		this.addContainerProperty("Hash", String.class, null);
	}
	
	
}
