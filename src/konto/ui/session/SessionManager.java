package konto.ui.session;

import java.io.Serializable;
import java.util.HashMap;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.data.Item;
import com.vaadin.server.VaadinSession;

import konto.data.container.CategoryContainer;
import konto.data.container.KontoContainer;
import konto.data.container.PaymentContainer;
import konto.data.container.TransaktionsContainer;
import konto.data.model.LoginUser;

/**
 * get User information from Session
 * @author lpichle
 *
 */
public class SessionManager implements Serializable {

    private static final long serialVersionUID = 1L;

    public static LoginUser getUser() {
        if (VaadinSession.getCurrent().getAttribute("user") instanceof LoginUser) {
            LoginUser result = (LoginUser) VaadinSession.getCurrent().getAttribute("user");
            return result;
        }
        
        return null;
    }

    public static void setUser(LoginUser user) {
        if(user!=null)
        try {
            VaadinSession.getCurrent().getLockInstance().lock();
            VaadinSession.getCurrent().setAttribute("user", user);
        } finally {
            VaadinSession.getCurrent().getLockInstance().unlock();
        }
    }
    
    public static TransaktionsContainer getTransaktionsContainer() {
        if (VaadinSession.getCurrent().getAttribute("transaktion") instanceof TransaktionsContainer) {
            TransaktionsContainer container = (TransaktionsContainer) VaadinSession.getCurrent().getAttribute("transaktion");
            return container;
        }
        
        return null;
    }
    
    public static void setTransaktionsContainer(TransaktionsContainer container) {
	if (container != null)
	try {
            VaadinSession.getCurrent().getLockInstance().lock();
            VaadinSession.getCurrent().setAttribute("transaktion", container);
	} finally {
	    VaadinSession.getCurrent().getLockInstance().unlock();
	}
	
    }
    
    public static PaymentContainer getPaymentContainer() {
        if (VaadinSession.getCurrent().getAttribute("payment") instanceof PaymentContainer) {
            PaymentContainer container = (PaymentContainer) VaadinSession.getCurrent().getAttribute("payment");
            return container;
        }
        
        return null;
    }
    
    public static void setPaymentContainer(PaymentContainer container) {
	if (container != null)
	try {
            VaadinSession.getCurrent().getLockInstance().lock();
            VaadinSession.getCurrent().setAttribute("payment", container);
	} finally {
	    VaadinSession.getCurrent().getLockInstance().unlock();
	}
	
    }
    
    public static KontoContainer getKontoContainer() {
        if (VaadinSession.getCurrent().getAttribute("konto") instanceof KontoContainer) {
            KontoContainer container = (KontoContainer) VaadinSession.getCurrent().getAttribute("konto");
            return container;
        }
        
        return null;
    }
    
    public static void setKontoContainer(KontoContainer container) {
	if (container != null) {
	    // create Hashmap to store ID's and Name
	    HashMap<Integer, String> kontoMap = new HashMap<Integer, String>();
	    for (int i = 1; i <= container.size(); i++) {
		Item item = container.getItem(i);
		int id = (Integer) item.getItemProperty("ID").getValue();
		String text = (String) item.getItemProperty("Beschreibung").getValue();
		kontoMap.put(id, text);
	    }

	    try {
		VaadinSession.getCurrent().getLockInstance().lock();
		VaadinSession.getCurrent().setAttribute("konto", container);
		VaadinSession.getCurrent().setAttribute("kontoMap", kontoMap);
	    } finally {
		VaadinSession.getCurrent().getLockInstance().unlock();
	    }
	}

    }
    
    public static HashMap<Integer, String> getKontoMap() {
	if (VaadinSession.getCurrent().getAttribute("kontoMap") instanceof HashMap) {
            @SuppressWarnings("unchecked")
	    HashMap<Integer, String> map = (HashMap<Integer, String>) VaadinSession.getCurrent().getAttribute("kontoMap");
            return map;
        }
        
        return null;
    }
    
    
    public static CategoryContainer getCategoryContainer() {
        if (VaadinSession.getCurrent().getAttribute("category") instanceof CategoryContainer) {
            CategoryContainer container = (CategoryContainer) VaadinSession.getCurrent().getAttribute("category");
            return container;
        }
        
        return null;
    }
    
    public static void setCategoryContainer(CategoryContainer container) {
	if (container != null) {
	    
	    // create Hashmap to store ID's and Name
	    HashMap<Integer, String> categoryMap = new HashMap<Integer, String>();
	    for (int i = 1; i <= container.size(); i++) {
		Item item = container.getItem(i);
		int id = (Integer) item.getItemProperty("ID").getValue();
		String text = (String) item.getItemProperty("Text").getValue();
		categoryMap.put(id, text);
		System.out.println("Stored categories: " +categoryMap);
	    }

	    try {
		VaadinSession.getCurrent().getLockInstance().lock();
		VaadinSession.getCurrent().setAttribute("category", container);
		VaadinSession.getCurrent().setAttribute("categoryMap", categoryMap);
	    } finally {
		VaadinSession.getCurrent().getLockInstance().unlock();
	    }
	}

    }
    
    public static HashMap<Integer, String> getCategoryMap() {
	if (VaadinSession.getCurrent().getAttribute("categoryMap") instanceof HashMap) {
            @SuppressWarnings("unchecked")
	    HashMap<Integer, String> map = (HashMap<Integer, String>) VaadinSession.getCurrent().getAttribute("categoryMap");
            return map;
        }
        
        return null;
    }
    
}
