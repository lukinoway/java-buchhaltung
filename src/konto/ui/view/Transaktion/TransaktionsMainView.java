package konto.ui.view.Transaktion;

import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.vaadin.haijian.ExcelExporter;
import org.vaadin.haijian.PdfExporter;
import org.vaadin.teemu.VaadinIcons;

import com.itextpdf.text.pdf.PdfPTable;
import com.vaadin.data.Container.Indexed;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
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
    
    Button addTransaktionBtn = new Button();
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
	transaktionsgrid.setHeight(UI.getCurrent().getPage().getBrowserWindowHeight()-350, Unit.PIXELS);

	searchBar = new TransaktionsSearchBar();
	this.addComponent(searchBar);
	this.addComponent(transaktionsgrid);

	addTransaktionBtn.addClickListener(new ClickListener() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void buttonClick(ClickEvent event) {
		TransaktionWindow w = new TransaktionWindow();
		UI.getCurrent().addWindow(w);
		w.focus();
	    }
	});

	this.addComponent(addTransaktionBtn);
	this.setComponentAlignment(addTransaktionBtn, Alignment.BOTTOM_CENTER);
	addTransaktionBtn.setIcon(VaadinIcons.PLUS_CIRCLE);
	addTransaktionBtn.setStyleName("addButton");
	addTransaktionBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS);
	//addTransaktionBtn.setClickShortcut(KeyCode.A);
	
	
	// prepare for export
	Indexed trData = transaktionsgrid.getContainerDataSource();
	trData.removeContainerProperty("delete");
	
	// export Button
	ExcelExporter excelExport = new ExcelExporter(trData, trData.getContainerPropertyIds().toArray());
	excelExport.setCaption("Export");
	excelExport.setIcon(VaadinIcons.DOWNLOAD);
	this.addComponent(excelExport);
	this.setComponentAlignment(excelExport, Alignment.BOTTOM_CENTER);
	
    }

}
