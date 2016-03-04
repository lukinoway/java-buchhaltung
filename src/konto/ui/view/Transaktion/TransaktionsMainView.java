package konto.ui.view.Transaktion;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.vaadin.teemu.VaadinIcons;

import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import konto.data.DBUtil.ITransaktion;
import konto.data.DBUtil.TransaktionDBUtil;
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

    public TransaktionsMainView() {
	
	// create test data
	createTestData();
	
	// set data
	this.setWidth(100, Unit.PERCENTAGE);
	
	transaktionsgrid = new TransaktionsGrid(container);
	
	searchBar.setWidth(100, Unit.PERCENTAGE);
	searchBar.searchBtn.addClickListener(new Button.ClickListener() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void buttonClick(ClickEvent event) {
		LocalDate fromDate = searchBar.getFromDate();
		LocalDate toDate = searchBar.getToDate();

		System.out.println("ButtonClick - von: " + fromDate);
		System.out.println("ButtonClick - bis: " + toDate);

//		TransaktionsContainer test = new TransaktionsContainer(
//			transaktion.selectDataByDate(fromDate, toDate, 1));
		System.out.println("ButtonClick - back from dbUtil");
		transaktionsgrid.setWidth(100, Unit.PERCENTAGE);
	    }
	});
	
	this.addComponent(searchBar);
	this.addComponent(transaktionsgrid);
	
	
	// add new Button
	Button addTransaktionBtn = new Button();
	addTransaktionBtn.addClickListener(new Button.ClickListener() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void buttonClick(ClickEvent event) {
		NewTransaktionWindow w = new NewTransaktionWindow(container);
		UI.getCurrent().addWindow(w);
		w.focus();
	    }
	});
	
	this.addComponent(addTransaktionBtn);
	addTransaktionBtn.setIcon(VaadinIcons.PLUS_CIRCLE);
	addTransaktionBtn.setStyleName("addButton");

	try {
	    container.addTransaktion(new Transaktion(LocalDate.now(), 60.0, "text5"));
	} catch (NoSuchAlgorithmException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	
    }
    
    private void createTestData() {
	try {
	    ArrayList<Transaktion> collector = new ArrayList<Transaktion>();
		    
		    collector.add(new Transaktion(LocalDate.now(), 20.0, "text1"));
		    collector.add(new Transaktion(LocalDate.now(), 30.0, "text2"));
		    collector.add(new Transaktion(LocalDate.now(), 40.0, "text3"));
		    collector.add(new Transaktion(LocalDate.now(), 50.0, "text4"));
	    container = new TransaktionsContainer(collector);
	} catch (NoSuchAlgorithmException e) { 
	    e.printStackTrace();
	}
    }

}
