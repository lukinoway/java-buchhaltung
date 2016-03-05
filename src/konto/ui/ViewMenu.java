package konto.ui;

import org.vaadin.teemu.VaadinIcons;

import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.VerticalLayout;

import konto.ui.view.Category.CategoryMainView;
import konto.ui.view.Konto.KontoMainView;
import konto.ui.view.Transaktion.TransaktionsMainView;
import konto.ui.view.User.NewUserWindow;

public class ViewMenu extends VerticalLayout {

	private static final long serialVersionUID = 1L;
	Button overView = new Button("Übersicht");
	Button typeData = new Button("Kategorien");
	Button kontoBtn = new Button("Konto's");
	Button addUser = new Button("Neuer User");
	
    public ViewMenu() {
    	
    	this.addComponent(overView);
    	overView.setStyleName("navigationBtn");
    	
    	overView.setIcon(VaadinIcons.LINES_LIST);
    	
    	this.addComponent(typeData);
    	typeData.setStyleName("navigationBtn");
    	//typeData.setIcon(VaadinIcons.OPTIONS);
    	
    	this.addComponent(kontoBtn);
    	kontoBtn.setStyleName("navigationBtn");
    	kontoBtn.setIcon(VaadinIcons.CREDIT_CARD);
    	
    	this.addComponent(addUser);
    	addUser.setStyleName("navigationBtn");
    	addUser.setIcon(VaadinIcons.USER);
    	
    	
    	
    	// add actions
    	overView.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				((MainApp) UI.getCurrent()).setMainView(TransaktionsMainView.class.getName());
			}	
    	});
    	
    	typeData.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				((MainApp) UI.getCurrent()).setMainView(CategoryMainView.class.getName());
			}	
    	});
    	
    	kontoBtn.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				((MainApp) UI.getCurrent()).setMainView(KontoMainView.class.getName());
			}	
    	});
    	
    	addUser.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				NewUserWindow w = new NewUserWindow();
				UI.getCurrent().addWindow(w);
				w.focus();	
			}	
    	});
    	
    }
    
}
