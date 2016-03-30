package konto.ui.view.Payment;

import org.vaadin.teemu.VaadinIcons;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

import konto.data.DBUtil.IPayment;
import konto.data.DBUtil.PaymentDBUtil;
import konto.data.container.PaymentContainer;
import konto.data.model.LoginUser;
import konto.data.model.PaymentOrder;
import konto.ui.session.SessionManager;

/**
 * Class which handles Payment orders between different Users
 * @author lpichle
 *
 */
public class PaymentMainView extends VerticalLayout{

    private static final long serialVersionUID = 1L;
    PaymentGrid grid;
    Button addPaymentBtn = new Button();
    LoginUser user;
    PaymentContainer container;
    
    IPayment paymentUtil = new PaymentDBUtil();
    
    public PaymentMainView() {
	this.user = SessionManager.getUser();

	// create container and store in session
	container = paymentUtil.getAllPaymentsForUser(user);
	SessionManager.setPaymentContainer(container);
	
	grid = new PaymentGrid(container);
	this.addComponent(grid);
	calcGridHeight();

	addPaymentBtn.setIcon(VaadinIcons.PLUS_CIRCLE);
	addPaymentBtn.setStyleName("addButton");
	addPaymentBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS);
	addPaymentBtn.setClickShortcut(KeyCode.A);
	this.addComponent(addPaymentBtn);
	this.setComponentAlignment(addPaymentBtn, Alignment.BOTTOM_CENTER);

	addPaymentBtn.addClickListener(new ClickListener() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void buttonClick(ClickEvent event) {

		PaymentWindow w = new PaymentWindow();
		UI.getCurrent().addWindow(w);
		w.focus();

	    }

	});
    }
    
    /**
     * reload data
     */
    public void loadPayments() {
	refillContainer(paymentUtil.getAllPaymentsForUser(user));
    }
    
    /**
     * helper function to refill container
     * @param paymentContainer
     */
    private void refillContainer(PaymentContainer paymentContainer) {
	container.removeAllItems();
	for (int i = 1; i <= paymentContainer.size(); i++) {
	    PaymentOrder temp = paymentContainer.buildPayment(paymentContainer.getItem(i));
	    container.addPaymentLocal(temp);
	}
    }
    
    public void calcGridHeight() {
	grid.setHeight(UI.getCurrent().getPage().getBrowserWindowHeight()-100, Unit.PIXELS);
    }

}
