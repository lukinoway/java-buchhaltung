package konto.ui;

import org.vaadin.teemu.VaadinIcons;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class SideBar extends HorizontalLayout {

    VerticalLayout main = new VerticalLayout();
    VerticalLayout functionView = new VerticalLayout();

    private static final long serialVersionUID = 1L;
    Label header = new Label("HomeApp");
    Label currentUser = new Label("");
    ViewMenu menu = new ViewMenu();
    Button expanderBtn = new Button();

    boolean expanded;

    public SideBar() {

	expanded = true;

	this.addComponent(main);
	this.addComponent(functionView);

	header.setStyleName("h1");
	main.addComponent(header);

	// show User name:
	main.addComponent(currentUser);
	main.setComponentAlignment(currentUser, Alignment.TOP_LEFT);
	currentUser.setStyleName("h2");

	main.addComponent(menu);
	main.setWidth(200, Unit.PIXELS);
	main.setComponentAlignment(menu, Alignment.TOP_LEFT);

	setHeight(100, Unit.PERCENTAGE);

	expanderBtn.setIcon(VaadinIcons.BACKWARDS);
	expanderBtn.addClickListener(new ClickListener() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void buttonClick(ClickEvent event) {
		changeView();
	    }

	});

	functionView.addComponent(expanderBtn);
	functionView.setWidth(50, Unit.PIXELS);
	functionView.setHeight(100, Unit.PERCENTAGE);
	functionView.setComponentAlignment(expanderBtn, Alignment.MIDDLE_RIGHT);

	addStyleName("sidebar");

    }

    private void changeView() {
	if (expanded) {
	    this.removeComponent(main);
	    ;
	    expanderBtn.setIcon(VaadinIcons.FORWARD);
	    expanded = false;
	} else {
	    this.removeAllComponents();
	    this.addComponent(main);
	    this.addComponent(functionView);
	    expanderBtn.setIcon(VaadinIcons.BACKWARDS);
	    expanded = true;
	}
    }

    public void changeUserLabel(String userName) {
	currentUser.setValue("USER: " + userName);
    }

}
