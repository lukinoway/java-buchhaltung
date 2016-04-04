package konto.ui;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;

import konto.ui.SideBar;
import konto.ui.session.SessionManager;
import konto.ui.view.Category.CategoryMainView;
import konto.ui.view.Konto.KontoMainView;
import konto.ui.view.Payment.PaymentMainView;
import konto.ui.view.Transaktion.TransaktionsMainView;

import com.vaadin.ui.*;

@Theme("mytheme")
public class MainApp extends UI {

    private static final long serialVersionUID = 1L;
    // layout part
    HorizontalLayout main = new HorizontalLayout();

    LoginForm loginForm = new LoginForm();
    VerticalLayout shownView = new VerticalLayout();
    SideBar sideBar = new SideBar();
    private CategoryMainView categoryView;
    private TransaktionsMainView transaktionsView;
    private KontoMainView kontoView;
    private PaymentMainView paymentView;
    private String currentView;

    @Override
    protected void init(VaadinRequest request) {

	main.setWidth(100, Unit.PERCENTAGE);
	main.setHeight(100, Unit.PERCENTAGE);
	main.addComponent(loginForm);
	main.setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);

	setContent(main);
    }

    /**
     * Switch between main views
     * 
     * @param view
     */
    public void setMainView(String view) {
	if (!currentView.equals(view)) {
	    shownView.removeAllComponents();
	    if (view.equals(TransaktionsMainView.class.getName())) {
		shownView.addComponent(transaktionsView);
		transaktionsView.calGridHeight();
		transaktionsView.rebuildActionBar();
		currentView = view;
	    }
	    if (view.equals(CategoryMainView.class.getName())) {
		shownView.addComponent(categoryView);
		categoryView.calcGridHeight();
		currentView = view;
	    }
	    if (view.equals(KontoMainView.class.getName())) {
		shownView.addComponent(kontoView);
		currentView = view;
	    }
	    if (view.equals(PaymentMainView.class.getName())) {
		shownView.addComponent(paymentView);
		paymentView.loadPayments();
		paymentView.calcGridHeight();
		currentView = view;
	    }
	}
    }

    public void buildMainView() {
	// show content only to valid users
	if (loginForm.isValidLogin()) {

	    sideBar.changeUserLabel(SessionManager.getUser().getUserName());

	    // need to wait for current User
	    kontoView = new KontoMainView();
	    categoryView = new CategoryMainView();
	    transaktionsView = new TransaktionsMainView();
	    paymentView = new PaymentMainView();
	    
	    

	    main.removeAllComponents();
	    main.setSizeUndefined();

	    shownView.addComponent(transaktionsView);
	    currentView = transaktionsView.getClass().getName();

	    main.addComponent(sideBar);
	    main.addComponent(shownView);
	    shownView.setWidth(100, Unit.PERCENTAGE);

	    setContent(main);
	    main.setHeight(100, Unit.PERCENTAGE);

	}
    }
    
//    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
//    @VaadinServletConfiguration(ui = MainApp.class, productionMode = false)
//    public static class MyUIServlet extends VaadinServlet {
//    }

}
