package konto.ui.session;

import java.io.Serializable;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.server.VaadinSession;

import konto.data.container.CategoryContainer;
import konto.data.container.KontoContainer;
import konto.data.container.TransaktionsContainer;
import konto.data.model.LoginUser;

/**
 * get User information from Session
 * @author lpichle
 *
 */
@PreserveOnRefresh
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
    
    public static KontoContainer getKontoContainer() {
        if (VaadinSession.getCurrent().getAttribute("konto") instanceof KontoContainer) {
            KontoContainer container = (KontoContainer) VaadinSession.getCurrent().getAttribute("konto");
            return container;
        }
        
        return null;
    }
    
    public static void setKontoContainer(KontoContainer container) {
	if (container != null)
	try {
            VaadinSession.getCurrent().getLockInstance().lock();
            VaadinSession.getCurrent().setAttribute("konto", container);
	} finally {
	    VaadinSession.getCurrent().getLockInstance().unlock();
	}
	
    }
    
    public static CategoryContainer getCategoryContainer() {
        if (VaadinSession.getCurrent().getAttribute("category") instanceof CategoryContainer) {
            CategoryContainer container = (CategoryContainer) VaadinSession.getCurrent().getAttribute("category");
            return container;
        }
        
        return null;
    }
    
    public static void setCategoryContainer(CategoryContainer container) {
	if (container != null)
	try {
            VaadinSession.getCurrent().getLockInstance().lock();
            VaadinSession.getCurrent().setAttribute("category", container);
	} finally {
	    VaadinSession.getCurrent().getLockInstance().unlock();
	}
	
    }
    
}
