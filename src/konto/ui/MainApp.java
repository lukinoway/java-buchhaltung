package konto.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;

import konto.ui.SideBar;
import konto.ui.session.SessionManager;
import konto.ui.view.Category.CategoryMainView;
import konto.ui.view.Konto.KontoMainView;
import konto.ui.view.Transaktion.TransaktionsMainView;

import com.vaadin.ui.*;

@Theme("mytheme")
public class MainApp extends UI {
    

    private static final long serialVersionUID = 1L;
    // layout part
    HorizontalLayout main = new HorizontalLayout();
    
    LoginForm loginForm = new LoginForm();
    VerticalLayout shownView = new VerticalLayout();
    SideBar sideBar = new SideBar();
    TransaktionsMainView transaktionsView;
    CategoryMainView categoryView = new CategoryMainView();
    private KontoMainView kontoView;
    private String currentView;

    @Override
    protected void init(VaadinRequest request) {

    main.setWidth(100, Unit.PERCENTAGE);
    main.setHeight(100, Unit.PERCENTAGE);
    main.addComponent(loginForm);
    main.setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);
    	
	setContent(main);
    }
    
    
    /**
     * Switch between main views
     * @param view
     */
    public void setMainView(String view) {
    	if (!currentView.equals(view)) {
    		shownView.removeAllComponents();
    		if (view.equals(TransaktionsMainView.class.getName())) {
    			shownView.addComponent(transaktionsView);
    			currentView = view;
    		}
    		if (view.equals(CategoryMainView.class.getName())) {
    			shownView.addComponent(categoryView);
    			currentView = view;
    		}
    		if (view.equals(KontoMainView.class.getName())) {
    			shownView.addComponent(kontoView);
    			currentView = view;
    		}
    	}
    }
    
    public void buildMainView() {
        // show content only to valid users
        if (loginForm.isValidLogin()) {	
        	
    	    sideBar.changeUserLabel(SessionManager.getUser().getUserName());
    	    
    	    // need to wait for current User
    	    kontoView = new KontoMainView();
    	    transaktionsView = new TransaktionsMainView();
        	
        	main.removeAllComponents();
        	main.setSizeUndefined();
        	
    		shownView.addComponent(transaktionsView);
    		currentView = transaktionsView.getClass().getName();
    		
    		main.addComponent(sideBar);
    		main.addComponent(shownView);   
    		shownView.setWidth(100, Unit.PERCENTAGE);
    		
    		setContent(main);
    	    main.setHeight(100, Unit.PERCENTAGE);
    	      
        }
    }

}
