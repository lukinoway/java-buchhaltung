package konto.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;

import konto.ui.SideBar;
import konto.ui.view.Category.CategoryMainView;
import konto.ui.view.Transaktion.TransaktionsMainView;

import com.vaadin.ui.*;

@Theme("mytheme")
public class MainApp extends UI {
    

    private static final long serialVersionUID = 1L;
    // layout part
    HorizontalLayout main = new HorizontalLayout();
    VerticalLayout shownView = new VerticalLayout();
    SideBar sideBar = new SideBar();
    TransaktionsMainView transaktionsView = new TransaktionsMainView();
    CategoryMainView categoryView = new CategoryMainView();
    private String currentView;

    @Override
    protected void init(VaadinRequest request) {

	// Main View
	shownView.addComponent(transaktionsView);
	currentView = transaktionsView.getClass().getName();
	
	main.addComponent(sideBar);
	main.addComponent(shownView);
	main.setHeight(100, Unit.PERCENTAGE);
	setContent(main);
    }
    
    
    public void setMainView(String view) {
    	if (!currentView.equals(view)) {
    		shownView.removeAllComponents();
    		if (view.equals(transaktionsView.getClass().getName())) {
    			shownView.addComponent(transaktionsView);
    		}
    		if (view.equals(categoryView.getClass().getName())) {
    			shownView.addComponent(categoryView);
    		}
    	}
    }
}
