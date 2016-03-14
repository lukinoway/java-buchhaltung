package konto.ui.view.Transaktion;

import org.vaadin.haijian.PdfExporter;
import org.vaadin.teemu.VaadinIcons;

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

	searchBar = new TransaktionsSearchBar();
	this.addComponent(searchBar);
	this.addComponent(transaktionsgrid);

	addTransaktionBtn.addClickListener(new ClickListener() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void buttonClick(ClickEvent event) {
		NewTransaktionWindow w = new NewTransaktionWindow();
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
	
	
	// export Button
	PdfExporter pdfExport = new PdfExporter(transaktionsgrid.getContainerDataSource(), transaktionsgrid.getContainerDataSource().getContainerPropertyIds().toArray());
	pdfExport.setCaption("Export");
	pdfExport.setWithBorder(true);
	pdfExport.setIcon(VaadinIcons.DOWNLOAD);
	this.addComponent(pdfExport);
	this.setComponentAlignment(pdfExport, Alignment.BOTTOM_CENTER);
	
    }

}
