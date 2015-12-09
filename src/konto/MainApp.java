package konto;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;

import org.jsoup.select.Collector;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import konto.MenuBar;
import konto.model.Transaktion;
import konto.view.TransaktionsGrid;

import com.vaadin.ui.*;


public class MainApp extends UI {

    @Override
    protected void init(VaadinRequest request) {
    	
        VerticalLayout view = new VerticalLayout();
        view.setWidth(150, Unit.PIXELS);
        VerticalLayout view2 = new VerticalLayout();
        MenuBar menu = new MenuBar();
        view.addComponent(menu);
        
        Container.Indexed indexed = null;
        
        // some test data
        try {
			Collection<Transaktion> collector = Arrays.asList(
					new Transaktion(LocalDate.now(), 20.0, "text1"),
					new Transaktion(LocalDate.now(), 30.0, "text2"),
					new Transaktion(LocalDate.now(), 40.0, "text3"),
					new Transaktion(LocalDate.now(), 50.0, "text4")
			);
			indexed = new BeanItemContainer<>(Transaktion.class, collector);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        

        TransaktionsGrid tab = new TransaktionsGrid(indexed);
        tab.setWidth(100, Unit.PERCENTAGE);
        
    	Panel panel = new Panel(tab);
    	panel.setWidth(1000, Unit.PIXELS);
        
        HorizontalLayout layout = new HorizontalLayout(panel);
        
        // Main View
        layout.setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
        layout.setMargin(true);
        layout.setExpandRatio(panel, 1.0f);
        view2.addComponent(layout);
        
        HorizontalLayout main = new HorizontalLayout();
        
        main.addComponent(view);
        main.addComponent(view2);
        setContent(main);
    }
}

