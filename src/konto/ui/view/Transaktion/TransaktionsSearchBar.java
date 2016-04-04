package konto.ui.view.Transaktion;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.vaadin.teemu.VaadinIcons;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
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
    
    String selectedOption;

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
	searchType.addItems("AlleDaten", "von-bis Übersicht", "Monatsübersicht", "Jahresübersicht");
	searchType.setNullSelectionAllowed(false);
	searchType.select("AlleDaten");
	buildSearchElements(searchType.getValue().toString());
	

	this.addComponent(nameLbl);
	this.setComponentAlignment(nameLbl, Alignment.TOP_LEFT);
	
	optionLayout.addComponent(searchType);
	this.addComponent(optionLayout);
	this.addComponent(searchElements);
	
	this.addComponent(searchBtn);
	this.setComponentAlignment(searchBtn, Alignment.MIDDLE_RIGHT);
	searchBtn.setIcon(VaadinIcons.SEARCH);
	searchBtn.addStyleName(ValoTheme.BUTTON_FRIENDLY);
	searchBtn.setClickShortcut(KeyCode.ENTER);
	
	
	// searchbutton
	searchBtn.addClickListener(new Button.ClickListener() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void buttonClick(ClickEvent event) {
		
		loadData();
	    }
	});
	
	
	// option buttons
	searchType.addValueChangeListener(new ValueChangeListener() {

	    @Override
	    public void valueChange(ValueChangeEvent event) {
		buildSearchElements(searchType.getValue().toString());
	    }
	    
	});
	
    }

    private LocalDate getFromDate() {
	LocalDate returnDate;
	if (fromDate.getValue() == null) {
	    returnDate = LocalDate.parse("2010-01-01");
	} else {
	    returnDate = LocalDateTime.ofInstant(fromDate.getValue().toInstant(), ZoneId.systemDefault()).toLocalDate();
	}
	return returnDate;

    }

    private LocalDate getToDate() {
	LocalDate returnDate;
	if (toDate.getValue() == null) {
	    returnDate = LocalDate.now();
	} else {
	    returnDate = LocalDateTime.ofInstant(toDate.getValue().toInstant(), ZoneId.systemDefault()).toLocalDate();
	}
	return returnDate;
    }
    
    private LocalDate getYearMonth() {
	LocalDate returnDate;
	if (monthYear.getValue() == null) {
	    returnDate = LocalDate.now();
	} else {
	    returnDate = LocalDateTime.ofInstant(monthYear.getValue().toInstant(), ZoneId.systemDefault()).toLocalDate();
	}
	return returnDate;
    }
    
    private LocalDate getYear() {
	LocalDate returnDate;
	if (year.getValue() == null) {
	    returnDate = LocalDate.now();
	} else {
	    returnDate = LocalDateTime.ofInstant(year.getValue().toInstant(), ZoneId.systemDefault()).toLocalDate();
	}
	return returnDate;
    }
    
    /**
     * load data from DB
     */
    private void loadData() {
	System.out.print("load data: " + selectedOption);
	// check main input
	int kontoId = 0;
	int categoryId = 0;
	if (kontoBox.getValue() != null) {
	    kontoId = kontoBox.getComboBoxIDValue();
	}
	if (categoryBox.getValue() != null) {
	    categoryId = categoryBox.getComboBoxIDValue();
	}
	
	// now decide which query should be run
	if (selectedOption.equals("von-bis Übersicht")) {
	    refillContainer(transaktionsUtil.getTransaktionsForDateKontoCategory(getFromDate(), getToDate(), kontoId, categoryId));
	}
	if (selectedOption.equals("Monatsübersicht")) {
	    refillContainer(transaktionsUtil.getTransaktionsForMonthKontoCategory(getYearMonth(), kontoId, categoryId));
	}
	if (selectedOption.equals("Jahresübersicht")) {
	    refillContainer(transaktionsUtil.getTransaktionsForYearKontoCategory(getYear(), kontoId, categoryId));
	} 
	if (selectedOption.equals("AlleDaten")) {
	    if (kontoId > 0 || categoryId > 0) {
		refillContainer(transaktionsUtil.getTransaktionsForKontoCategory(kontoId, categoryId));
	    }
	}
	// get all data if there is something wrong
	if (kontoId == 0 && categoryId == 0) {
	    refillContainer(transaktionsUtil.getAllTransaktionsForUser(user));
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
    
    /**
     * build search elements
     * @param option
     */
    private void buildSearchElements(String option) {
	searchElements.removeAllComponents();
	
	// visible in all searchbars
	searchElements.addComponent(kontoBox);
	kontoBox.setNullSelectionAllowed(true);
	kontoBox.addStyleName(ValoTheme.COMBOBOX_SMALL);
	
	searchElements.addComponent(categoryBox);
	categoryBox.setNullSelectionAllowed(true);
	categoryBox.addStyleName(ValoTheme.COMBOBOX_SMALL);
	
	System.out.println("option button: " + option);
	// check for selected option
	if (option.equals("von-bis Übersicht")) {
	    searchElements.addComponent(fromDate);
	    searchElements.addComponent(toDate);
	    
	    fromDate.addStyleName(ValoTheme.DATEFIELD_SMALL);
	    toDate.addStyleName(ValoTheme.DATEFIELD_SMALL);
	}
	if (option.equals("Monatsübersicht")) {
	    searchElements.addComponent(monthYear);
	    monthYear.addStyleName(ValoTheme.DATEFIELD_SMALL);
	}
	if (option.equals("Jahresübersicht")) {
	    searchElements.addComponent(year);
	    year.addStyleName(ValoTheme.DATEFIELD_SMALL);
	} 
	if (option.equals("AlleDaten")) {

	}
	
	selectedOption = option;
    }

}
