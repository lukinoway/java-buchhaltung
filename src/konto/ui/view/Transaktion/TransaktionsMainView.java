package konto.ui.view.Transaktion;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.vaadin.teemu.VaadinIcons;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import konto.data.DBUtil.ITransaktion;
import konto.data.DBUtil.TransaktionDBUtil;
import konto.data.model.LoginUser;
import konto.data.model.Transaktion;

/**
 * This class should be the main view for my transkations grid
 * 
 * @author lpichle
 *
 */
public class TransaktionsMainView extends VerticalLayout {

    private static final long serialVersionUID = 1L;
//    ITransaktion transaktion = new TransaktionDBUtil();
    TransaktionsGrid transaktionsgrid;
    TransaktionsSearchBar searchBar = new TransaktionsSearchBar();
    
    private TransaktionsContainer container;
    
    LoginUser user;

    public TransaktionsMainView(LoginUser user) {
	
    	this.user = user;
    	
	// create test data
	createTestData();
	
	// set data
	this.setWidth(100, Unit.PERCENTAGE);
	
	transaktionsgrid = new TransaktionsGrid(container);
	
	
	this.addComponent(searchBar);
	this.addComponent(transaktionsgrid);
	
	
	// add new Button
	Button addTransaktionBtn = new Button();
	addTransaktionBtn.addClickListener(new Button.ClickListener() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void buttonClick(ClickEvent event) {
			NewTransaktionWindow w = new NewTransaktionWindow(container, user);
			UI.getCurrent().addWindow(w);
			w.focus();
	    }
	});
	
	this.addComponent(addTransaktionBtn);
	this.setComponentAlignment(addTransaktionBtn, Alignment.BOTTOM_CENTER);
	addTransaktionBtn.setIcon(VaadinIcons.PLUS_CIRCLE);
	addTransaktionBtn.setStyleName("addButton");

    }
    
    /**
     * Create some test data
     */
    private void createTestData() {
	try {
	    ArrayList<Transaktion> collector = new ArrayList<Transaktion>();
		    
		    collector.add(new Transaktion(LocalDate.now(), 20.0, "text1", 0, 0));
		    collector.add(new Transaktion(LocalDate.now(), 30.0, "text2", 0, 0));
		    collector.add(new Transaktion(LocalDate.now(), 40.0, "text3", 0, 0));
		    collector.add(new Transaktion(LocalDate.now(), 50.0, "text4", 0, 0));
	    container = new TransaktionsContainer(collector);
	} catch (NoSuchAlgorithmException e) { 
	    e.printStackTrace();
	}
    }

}
