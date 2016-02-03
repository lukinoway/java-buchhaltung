package konto.ui;

import com.vaadin.server.VaadinRequest;

import konto.ui.MenuBar;
import konto.ui.view.Transaktion.NewTransaktionWindow;
import konto.ui.view.Transaktion.TransaktionsMainView;

import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;

public class MainApp extends UI {

    private static final long serialVersionUID = 1L;
    // layout part
    HorizontalLayout main = new HorizontalLayout();
    VerticalLayout view = new VerticalLayout();
    VerticalLayout view2 = new VerticalLayout();
    MenuBar menu = new MenuBar();
    TransaktionsMainView transaktionsView = new TransaktionsMainView();

    @Override
    protected void init(VaadinRequest request) {

	view.setWidth(150, Unit.PIXELS);
	view.addComponent(menu);

	// some test data
	/*
	 * try { Collection<Transaktion> collector = Arrays.asList( new
	 * Transaktion(LocalDate.now(), 20.0, "text1"), new
	 * Transaktion(LocalDate.now(), 30.0, "text2"), new
	 * Transaktion(LocalDate.now(), 40.0, "text3"), new
	 * Transaktion(LocalDate.now(), 50.0, "text4") ); indexed = new
	 * BeanItemContainer<>(Transaktion.class, collector); } catch
	 * (NoSuchAlgorithmException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 */

	// Main View
	//view2.addComponent(transaktionsView);

	Button test = new Button("open Window");
	test.addClickListener(new Button.ClickListener() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void buttonClick(ClickEvent event) {
		NewTransaktionWindow w = new NewTransaktionWindow();
		UI.getCurrent().addWindow(w);
		w.focus();
	    }
	});
	view.addComponent(test);
	main.addComponent(view);
	main.addComponent(view2);
	setContent(main);
    }
}
