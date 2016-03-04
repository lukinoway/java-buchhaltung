package konto.ui;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class SideBar extends VerticalLayout {

    private static final long serialVersionUID = 1L;
    Label header = new Label("KontoAPP");
    ViewMenu menu = new ViewMenu();

    public SideBar() {

	header.setStyleName("h1");
	this.addComponent(header);
	setWidth(200, Unit.PIXELS);
	setHeight(100, Unit.PERCENTAGE);
	addStyleName("sidebar");
	
	this.addComponent(menu);
	
    }

}
