package konto.ui;

import org.vaadin.teemu.VaadinIcons;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.VerticalLayout;

public class ViewMenu extends VerticalLayout {

	private static final long serialVersionUID = 1L;
	Button overView = new Button("Übersicht");
	Button typeData = new Button("Stammdaten");
	
    public ViewMenu() {
    	
    	this.addComponent(overView);
    	overView.setStyleName("navigationBtn");
    	
    	overView.setIcon(VaadinIcons.LINES_LIST);
    	
    	this.addComponent(typeData);
    	typeData.setStyleName("navigationBtn");
    	//typeData.setIcon(VaadinIcons.OPTIONS);
    	
    	
    	// add actions
    	overView.addClickListener(new ClickListener() {


			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				// add view caller
			}
    		
    	});
    	
    	typeData.addClickListener(new ClickListener() {


			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				// add view caller
			}
    		
    	});
    	
    }
    
}
