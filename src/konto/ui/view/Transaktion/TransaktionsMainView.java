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

import konto.data.DBUtil.ITransaktion;
import konto.data.DBUtil.TransaktionDBUtil;
import konto.data.container.TransaktionsContainer;
import konto.data.model.LoginUser;
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
