package konto;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class MenuBar extends VerticalLayout {
	
	private static final long serialVersionUID = 1L;
	Label header = new Label("KontoAPP");
	
	public MenuBar() {
		
		header.setStyleName("h1");
		this.addComponent(header);
		this.setWidth(150, Unit.PIXELS);
		this.setHeight(100, Unit.PERCENTAGE);
		
	}

}
