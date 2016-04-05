package konto.ui.view.Transaktion;

import org.vaadin.haijian.ExcelExporter;
import org.vaadin.teemu.VaadinIcons;

import com.vaadin.data.Container.Indexed;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;

import konto.data.DBUtil.IReport;
import konto.data.DBUtil.ITransaktion;
import konto.data.DBUtil.ReportDBUtil;
import konto.data.DBUtil.TransaktionDBUtil;
import konto.data.container.TransaktionsContainer;
import konto.data.model.LoginUser;
import konto.report.ReportUtil;
import konto.ui.session.SessionManager;

/**
 * This class should be the main view for my transkations grid
 * 
 * @author lpichle
 *
 */
public class TransaktionsMainView extends VerticalLayout {

    private static final long serialVersionUID = 1L;
    ITransaktion transaktionUtil = new TransaktionDBUtil();
    TransaktionsGrid transaktionsgrid;
    TransaktionsSearchBar searchBar;
    HorizontalLayout actionBar = new HorizontalLayout();
    
    Button addTransaktionBtn = new Button();
    Button addTransferBtn = new Button();
    Button exportGridBtn = new Button("Export");

    private TransaktionsContainer container;
    private ReportUtil reportUtil;

    LoginUser user;

    public TransaktionsMainView() {

	this.user = SessionManager.getUser();

	// store data in session
	container = transaktionUtil.getAllTransaktionsForUser(user);
	SessionManager.setTransaktionsContainer(container);

	// set data
	this.setWidth(100, Unit.PERCENTAGE);

	transaktionsgrid = new TransaktionsGrid(container);
	calGridHeight();

	searchBar = new TransaktionsSearchBar();
	this.addComponent(searchBar);
	this.addComponent(transaktionsgrid);
	
	this.reportUtil = new ReportUtil();
	
	buildActionBar();

	
    }
    
    private void buildActionBar() {
	
	actionBar.addComponent(addTransaktionBtn);
	actionBar.setComponentAlignment(addTransaktionBtn, Alignment.MIDDLE_CENTER);
	addTransaktionBtn.setIcon(VaadinIcons.PLUS_CIRCLE);
	addTransaktionBtn.setStyleName("addButton");
	addTransaktionBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS);
	addTransaktionBtn.addClickListener(new ClickListener() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void buttonClick(ClickEvent event) {
		TransaktionWindow w = new TransaktionWindow();
		UI.getCurrent().addWindow(w);
		w.focus();
	    }
	});

	// only show button if there are more than 2 kontos
	if(SessionManager.getKontoMap().size()>1) {
	    actionBar.addComponent(addTransferBtn);
	    actionBar.setComponentAlignment(addTransferBtn, Alignment.MIDDLE_CENTER);
	}
	
	
	addTransferBtn.setIcon(VaadinIcons.EXCHANGE);
	addTransferBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS);
	addTransferBtn.addStyleName("icon");
	addTransferBtn.addClickListener(new ClickListener() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void buttonClick(ClickEvent event) {
		TransaktionsTransferWindow w = new TransaktionsTransferWindow();
		UI.getCurrent().addWindow(w);
		w.focus();
		
	    }
	    
	});

	// prepare for export
	Indexed trData = transaktionsgrid.getContainerDataSource();
	trData.removeContainerProperty("delete");
	
	// export Button
	ExcelExporter excelExport = new ExcelExporter(trData, trData.getContainerPropertyIds().toArray());
	excelExport.setCaption("Export");
	excelExport.setIcon(VaadinIcons.DOWNLOAD);
	actionBar.addComponent(excelExport);
	actionBar.setComponentAlignment(excelExport, Alignment.MIDDLE_CENTER);
	
	// report button
	Button reportBtn = new Button("Get Report");
	actionBar.addComponent(reportBtn);
	actionBar.setComponentAlignment(reportBtn, Alignment.MIDDLE_CENTER);
	reportBtn.addClickListener(new ClickListener() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void buttonClick(ClickEvent event) {
		IReport reportDBUtil = new ReportDBUtil();
		// get Option from SessionManager
		String option = SessionManager.getQueryType();
		System.out.println("Export Option: " + option);
		if(option == null) {
		    return;
		}
		if(option.equals("Monatsübersicht")) {
		    reportUtil.prepareForPdfReport(
		            reportDBUtil.getMonthReport(
		                    SessionManager.getUser(),
		                    SessionManager.getQueryDate()));
		}
		else if(option.equals("Jahresübersicht")) {
		    reportUtil.prepareForPdfReport(
		            reportDBUtil.getYearReport(
		                    SessionManager.getUser(),
		                    SessionManager.getQueryDate()));
		}
		else {
		    reportUtil.prepareForPdfReport(
			    transaktionUtil.getReport(SessionManager.getUser()));
		}
		
	    }
	    
	});
	//only to this the first time
	reportUtil.prepareForPdfReport(
		transaktionUtil.getReport(SessionManager.getUser()));
	reportUtil.extendButton(reportBtn);
	

	
	this.addComponent(actionBar);
	this.setComponentAlignment(actionBar, Alignment.BOTTOM_CENTER);
	
    }
    
    public void rebuildActionBar() {
	this.removeComponent(actionBar);
	actionBar.removeAllComponents();
	buildActionBar();
    }
    
    public void calGridHeight() {
	transaktionsgrid.setHeight(UI.getCurrent().getPage().getBrowserWindowHeight()-300, Unit.PIXELS);
    }

}
