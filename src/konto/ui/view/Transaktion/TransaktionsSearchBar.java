package konto.ui.view.Transaktion;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.vaadin.teemu.VaadinIcons;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import konto.data.DBUtil.ITransaktion;
import konto.data.DBUtil.TransaktionDBUtil;
import konto.data.container.TransaktionsContainer;
import konto.data.model.LoginUser;
import konto.data.model.Transaktion;
import konto.ui.elements.CategoryComboBox;
import konto.ui.elements.KontoComboBox;
import konto.ui.session.SessionManager;

public class TransaktionsSearchBar extends HorizontalLayout {

    private static final long serialVersionUID = 1L;

    String format = "yyyy-MM-dd";
    String formatMonth = "yyyy-MM";
    String formatYear = "yyyy";

    // GUI features
    Label nameLbl = new Label("SearchBar");
    
    VerticalLayout optionLayout = new VerticalLayout();
    OptionGroup searchType = new OptionGroup("Filter Auswahl");
    
    VerticalLayout searchElements = new VerticalLayout();
    KontoComboBox kontoBox = new KontoComboBox();
    CategoryComboBox categoryBox = new CategoryComboBox();
    DateField fromDate = new DateField("Von");
    DateField toDate = new DateField("Bis");
    DateField monthYear = new DateField("Monat");
    DateField year = new DateField("Jahr");
    Button searchBtn = new Button("Suche");
    
    
    LoginUser user;
    TransaktionsContainer container;
    TransaktionsGrid grid;
    
    ITransaktion transaktionsUtil = new TransaktionDBUtil();

    public TransaktionsSearchBar() {
	
	// set local data
	this.container = SessionManager.getTransaktionsContainer();
	this.user = SessionManager.getUser();
	
	// add style
	this.addStyleName("searchbar");
	this.setWidth(1000, Unit.PIXELS);
	
	// set datefield formats
	fromDate.setDateFormat(format);
	toDate.setDateFormat(format);
	monthYear.setDateFormat(formatMonth);
	year.setDateFormat(formatYear);

	nameLbl.setStyleName("h2");
	
	// add filter options
	searchType.addItems("von-bis Übersicht", "Monatsübersicht", "Jahresübersicht", "AlleDaten");
	

	this.addComponent(nameLbl);
	this.setComponentAlignment(nameLbl, Alignment.TOP_LEFT);
	
	optionLayout.addComponent(searchType);
	this.addComponent(optionLayout);
	this.addComponent(searchElements);
	
/*	this.addComponent(kontoBox);	
	this.addComponent(fromDate);
	this.addComponent(toDate);
	*/
	this.addComponent(searchBtn);
	this.setComponentAlignment(searchBtn, Alignment.MIDDLE_RIGHT);
	searchBtn.setIcon(VaadinIcons.SEARCH);
	
	
	// searchbutton
	searchBtn.addClickListener(new Button.ClickListener() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void buttonClick(ClickEvent event) {
		
		loadData();
		
/*		LocalDate fromDate = getFromDate();
		LocalDate toDate = getToDate();

		System.out.println("ButtonClick - von: " + fromDate);
		System.out.println("ButtonClick - bis: " + toDate);

//		TransaktionsContainer test = new TransaktionsContainer(
//			transaktion.selectDataByDate(fromDate, toDate, 1));
		System.out.println("ButtonClick - back from dbUtil");*/
		
		System.out.println("option button: " + searchType.getValue().toString());
	    }
	});
	
	
	// option buttons
	searchType.addValueChangeListener(new ValueChangeListener() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void valueChange(ValueChangeEvent event) {
		buildSearchElements(searchType.getValue().toString());
	    }
	    
	});
	
    }

    public LocalDate getFromDate() {
	LocalDate returnDate;
	if (fromDate.getValue() == null) {
	    returnDate = LocalDate.parse("2010-01-01");
	} else {
	    returnDate = LocalDateTime.ofInstant(fromDate.getValue().toInstant(), ZoneId.systemDefault()).toLocalDate();
	}
	return returnDate;

    }

    public LocalDate getToDate() {
	LocalDate returnDate;
	if (toDate.getValue() == null) {
	    returnDate = LocalDate.now();
	} else {
	    returnDate = LocalDateTime.ofInstant(toDate.getValue().toInstant(), ZoneId.systemDefault()).toLocalDate();
	}
	return returnDate;
    }
    
    
    private void loadData() {
	if (kontoBox != null || !(kontoBox.getValue() == null)) {
	    //refillContainer(transaktionsUtil.getTransaktionsForKonto(kontoBox.getComboBoxIDValue()));
	}
	else {
	    System.out.println("nothing to do");
	}
    }
    
    /**
     * helper function to refill container
     * @param newcontainer
     */
    private void refillContainer(TransaktionsContainer newcontainer) {
	container.removeAllItems();
	for (int i = 1; i <= newcontainer.size(); i++) {
	    Transaktion temp = newcontainer.buildTransaktion(newcontainer.getItem(i));
	    container.addTransaktionLocal(temp);
	}
    }
    
    private void buildSearchElements(String option) {
	searchElements.removeAllComponents();
	
	// visible in all searchbars
	searchElements.addComponent(kontoBox);
	kontoBox.setNullSelectionAllowed(true);
	
	searchElements.addComponent(categoryBox);
	categoryBox.setNullSelectionAllowed(true);
	
	System.out.println("option button: " + option);
	// check for selected option
	if (option.equals("von-bis Übersicht")) {
	    searchElements.addComponent(fromDate);
	    searchElements.addComponent(toDate);
	}
	if (option.equals("Monatsübersicht")) {
	    searchElements.addComponent(monthYear);
	}
	if (option.equals("Jahresübersicht")) {
	    searchElements.addComponent(year);
	} 
	if (option.equals("AlleDaten")) {
	    
	}
    }

}
