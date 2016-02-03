package konto.ui.view.Transaktion;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.vaadin.ui.Button;
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
    ITransaktion transaktion = new TransaktionDBUtil();
    TransaktionsGrid transaktionsgrid = new TransaktionsGrid();
    TransaktionsSearchBar searchBar = new TransaktionsSearchBar();
    TransaktionsContainer indexed;

    public TransaktionsMainView() {
	this.addComponent(searchBar);
	this.addComponent(transaktionsgrid);
	
	// create test data
	createTestData();
	
	// set data
	

	transaktionsgrid.setWidth(100, Unit.PERCENTAGE);
	searchBar.setWidth(100, Unit.PERCENTAGE);
	searchBar.searchBtn.addClickListener(new Button.ClickListener() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void buttonClick(ClickEvent event) {
		LocalDate fromDate = searchBar.getFromDate();
		LocalDate toDate = searchBar.getToDate();

		System.out.println("ButtonClick - von: " + fromDate);
		System.out.println("ButtonClick - bis: " + toDate);

		TransaktionsContainer test = new TransaktionsContainer(
			transaktion.selectDataByDate(fromDate, toDate, 1));
		System.out.println("ButtonClick - back from dbUtil");
		transaktionsgrid = new TransaktionsGrid(test);
		transaktionsgrid.setWidth(100, Unit.PERCENTAGE);
	    }
	});
	
	transaktionsgrid = new TransaktionsGrid(indexed);
    }
    
    private void createTestData() {
	try {
	    ArrayList<Transaktion> collector = new ArrayList<Transaktion>();
		    
		    collector.add(new Transaktion(LocalDate.now(), 20.0, "text1"));
		    collector.add(new Transaktion(LocalDate.now(), 30.0, "text2"));
		    collector.add(new Transaktion(LocalDate.now(), 40.0, "text3"));
		    collector.add(new Transaktion(LocalDate.now(), 50.0, "text4"));
	    indexed = new TransaktionsContainer(collector);
	} catch (NoSuchAlgorithmException e) { 
	    e.printStackTrace();
	}
    }

}
