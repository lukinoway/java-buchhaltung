package konto.view.Transaktion;

import java.time.LocalDate;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import konto.DBUtil.ITransaktion;
import konto.DBUtil.TransaktionDBUtil;

/**
 * This class should be the main view for my transkations grid
 * @author lpichle
 *
 */
public class TransaktionsMainView extends VerticalLayout{
	
	private static final long serialVersionUID = 1L;
	ITransaktion transaktion = new TransaktionDBUtil();
    TransaktionsGrid transaktionsgrid = new TransaktionsGrid();
    TransaktionsSearchBar searchBar = new TransaktionsSearchBar();
	
	public TransaktionsMainView () {
		
		this.addComponent(searchBar);
		this.addComponent(transaktionsgrid);
		
		
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
				
				TransaktionsContainer test = new TransaktionsContainer(transaktion.selectDataByDate(fromDate, toDate, 1));
				System.out.println("ButtonClick - back from dbUtil");
				transaktionsgrid = new TransaktionsGrid(test);
				transaktionsgrid.setWidth(100, Unit.PERCENTAGE);
			}
		});
	}

}
