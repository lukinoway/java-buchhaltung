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
	loadPayments();
	SessionManager.setPaymentContainer(container);
	
	grid = new PaymentGrid(container);
	this.addComponent(grid);

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
	container = paymentUtil.getAllPaymentsForUser(user);
    }

}
