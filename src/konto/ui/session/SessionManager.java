package konto.ui.session;

import java.io.Serializable;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.server.VaadinSession;

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
    
}
