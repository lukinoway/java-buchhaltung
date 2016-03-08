package konto.data.container;

import java.util.ArrayList;

import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;

import konto.data.DBUtil.IKonto;
import konto.data.DBUtil.KontoDBUtil;
import konto.data.model.Konto;

public class KontoContainer extends IndexedContainer {

	private static final long serialVersionUID = 1L;
	IKonto kontoUtil = new KontoDBUtil();
	

	/**
	 * Category data to store data
	 * @param categoryList
	 */
	@SuppressWarnings("unchecked")
	public KontoContainer(ArrayList<Konto> kontoList) {
		this.addContainerProperty("ID", Integer.class, null);
		this.addContainerProperty("Konto", String.class, null);
		this.addContainerProperty("Beschreibung", String.class, null);
		
		for (Konto konto : kontoList) {
			Object id = addItem();
			Item item = getItem(id);
			if (item != null) {
				item.getItemProperty("ID").setValue(konto.getKontoId());
				item.getItemProperty("Konto").setValue(konto.getKontoNr());
				item.getItemProperty("Beschreibung").setValue(konto.getKontoName());
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
		}
		
		
	}

	public void removeItemDB(Object itemId) {
		Item item = getItem(itemId);
		if (item != null) {
			int kontoId = (Integer) item.getItemProperty("ID").getValue();
			String kontoNr = (String) item.getItemProperty("Konto").getValue();
			String kontoText = (String) item.getItemProperty("Beschreibung").getValue();
			
			// delete from DB
			kontoUtil.deleteKonto(new Konto(kontoId, kontoNr, kontoText, 0));
			
			// now delete from List
			removeItem(itemId);
			
			System.out.println("delete Konto: " + kontoNr);
		}
		
	}
	
}
