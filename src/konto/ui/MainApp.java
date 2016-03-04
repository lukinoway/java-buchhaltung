package konto.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;

import konto.ui.SideBar;
import konto.ui.view.Transaktion.TransaktionsMainView;

import com.vaadin.ui.*;

@Theme("mytheme")
public class MainApp extends UI {
    

    private static final long serialVersionUID = 1L;
    // layout part
    HorizontalLayout main = new HorizontalLayout();
    VerticalLayout shownView = new VerticalLayout();
    SideBar sideBar = new SideBar();
    TransaktionsMainView transaktionsView = new TransaktionsMainView();

    @Override
    protected void init(VaadinRequest request) {

	// Main View
	shownView.addComponent(transaktionsView);

	main.addComponent(sideBar);
	main.addComponent(shownView);
	main.setHeight(100, Unit.PERCENTAGE);
	setContent(main);
    }
}
