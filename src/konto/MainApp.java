package konto;

import com.vaadin.server.VaadinRequest;
import konto.MenuBar;
import konto.view.TransaktionsTable;

import com.vaadin.ui.*;


public class MainApp extends UI {

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout view = new VerticalLayout();
        VerticalLayout view2 = new VerticalLayout();
        //view.addComponent(new Label("Hello Vaadin!"));
        MenuBar menu = new MenuBar();
        view.addComponent(menu);
        
        TransaktionsTable tab = new TransaktionsTable();
        tab.setSizeFull();
        
        view2.addComponent(tab);
        view.addComponent(view2);
        
        setContent(view);
    }
}

