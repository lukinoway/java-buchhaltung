package konto;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class MenuBar extends VerticalLayout {

    Label header = new Label("KontoAPP");

    public MenuBar() {

	header.setStyleName("h1");
	this.addComponent(header);
	this.setWidth(150, Unit.PIXELS);
    }

}
