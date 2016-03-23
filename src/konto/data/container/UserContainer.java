package konto.data.container;

import java.util.ArrayList;

import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;

import konto.data.model.Konto;
import konto.data.model.LoginUser;

public class UserContainer extends IndexedContainer{

    private static final long serialVersionUID = 1L;
    
    @SuppressWarnings("unchecked")
    public UserContainer(ArrayList<LoginUser> userList) {
	this.addContainerProperty("ID", Integer.class, null);
	this.addContainerProperty("Konto", String.class, null);
	this.addContainerProperty("Beschreibung", String.class, null);

	for (LoginUser user : userList) {
	    Object id = addItem();
	    Item item = getItem(id);
	    if (item != null) {
		item.getItemProperty("ID").setValue(konto.getKontoId());
		item.getItemProperty("Konto").setValue(konto.getKontoNr());
		item.getItemProperty("Beschreibung").setValue(konto.getKontoName());
	    }
	}
    }

}
