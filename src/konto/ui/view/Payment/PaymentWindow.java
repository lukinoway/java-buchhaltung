package konto.ui.view.Payment;

import java.time.LocalDate;

import com.vaadin.data.Item;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import konto.data.DBUtil.IKonto;
import konto.data.DBUtil.KontoDBUtil;
import konto.data.container.PaymentContainer;
import konto.data.model.PaymentOrder;
import konto.ui.elements.KontoComboBox;
import konto.ui.elements.UserComboBox;
import konto.ui.session.SessionManager;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class PaymentWindow extends Window{

    private static final long serialVersionUID = 1L;
    
    TextField paymentText = new TextField("Text");
    TextField paymentBetrag = new TextField("Betrag");
    Button saveBtn = new Button("speichern");
    Button cancelBtn = new Button("abbrechen");
    
    KontoComboBox creatorBox = new KontoComboBox();
    KontoComboBox requestBox = new KontoComboBox();
    UserComboBox userBox = new UserComboBox();
    
    GridLayout gridView = new GridLayout(2, 7);
    
    PaymentContainer container;
    PaymentOrder payment;
    private boolean update = false;
    private boolean paymentMode = false;
    Object itemId;
    
    IKonto kontoUtil = new KontoDBUtil();
    
    public PaymentWindow() {
	
	this.setCaption("Neue Zahlungsforderung");
	this.container = SessionManager.getPaymentContainer();
	buildGrid();
	
    }
    
    public PaymentWindow(Object itemId) {
	
	update = true;
	this.setCaption("Update Zahlungsforderung");
	this.container = SessionManager.getPaymentContainer();
	this.itemId = itemId;
	buildGrid();
	loadPayment(container.getItem(itemId));
    }
    

    private void buildGrid() {
	
	gridView.addComponent(paymentText, 0, 0, 1, 0);
	paymentText.setWidth(100, Unit.PERCENTAGE);

	gridView.addComponent(paymentBetrag, 0, 1, 0, 1);
	
	gridView.addComponent(creatorBox, 0, 2, 0, 2);
	creatorBox.setCaption("Empfänger");
	
//	gridView.addComponent(userBox, 0, 3, 0, 3 );
//	userBox.setCaption("Schuldner");
	
	gridView.addComponent(requestBox, 0, 3, 0, 3);
	requestBox.setCaption("Schuldner Konto");
	requestBox.setContainerDataSource(kontoUtil.getVisibleKontos(SessionManager.getUser()));

	gridView.addComponent(saveBtn, 0, 6, 0, 6);
	gridView.addComponent(cancelBtn, 1, 6, 1, 6);
	gridView.setMargin(true);
	
	saveBtn.setClickShortcut(KeyCode.ENTER);
	cancelBtn.setClickShortcut(KeyCode.ESCAPE);
	
	// add new entry
	saveBtn.addClickListener(new ClickListener() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void buttonClick(ClickEvent event) {
		if(update) {
		    updatePayment();
		}
		if(paymentMode) {
		    container.onPayment(itemId, payment);
		    PaymentWindow.this.close();
		}
		if(!paymentMode && !update) {
		    addPayment();
		}
	    }
	});

	// close window
	cancelBtn.addClickListener(new ClickListener() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void buttonClick(ClickEvent event) {
		PaymentWindow.this.close();

	    }

	});
	
	this.setContent(gridView);
	this.center();
    }
    
    private void loadPayment(Item item) {
	payment = this.container.buildPayment(item);

	paymentText.setValue(payment.getPaymentText());
	paymentBetrag.setValue(String.valueOf(payment.getBetrag()));

	if (SessionManager.getUser().getUserId() == kontoUtil.getUserIdforKonto(payment.getSchuldnerKontoId())) {
	    update = false;
	    paymentMode = true;
	    
	    this.setCaption("austehende Zahlung");
	    paymentText.setReadOnly(true);
	    paymentBetrag.setReadOnly(true);
	    
	    
	    gridView.removeComponent(creatorBox);
	    gridView.removeComponent(requestBox);

	    // add BOX with contact Information
	    TextArea transferInfo = new TextArea("Überweisungs Details");
	    transferInfo.setValue(kontoUtil.getTransferInformationforKonto(payment.getErstellerKontoId()));
	    transferInfo.setReadOnly(true);

	    gridView.addComponent(transferInfo, 0, 2, 1, 4);
	    transferInfo.setWidth(100, Unit.PERCENTAGE);

	    saveBtn.setCaption("Bezahlen");
	    saveBtn.addStyleName(ValoTheme.BUTTON_FRIENDLY);

	} else {
	    creatorBox.setComboBoxValue(payment.getErstellerKontoId());
	    requestBox.setComboBoxValue(payment.getSchuldnerKontoId());
	}
    }
    
    /**
     * Function to check if the input is sane
     */
    private boolean validateInput() {
	boolean valid = false;
	try {
	    if (paymentText == null | paymentText.getValue() == "") {
		paymentText.focus();
		throw new NullPointerException("paymentText fehlt");
	    }
	    if (paymentBetrag == null | paymentBetrag.getValue() == "") {
		paymentBetrag.focus();
		throw new NullPointerException("paymentBetrag fehlt");
	    }
	    if (!paymentBetrag.isEmpty()) {
		@SuppressWarnings("unused")
		double d = Double.parseDouble(paymentBetrag.getValue());
	    }
	    // seems that everything was OK
	    valid = true;
	} catch (NullPointerException e) {
	    System.out.println("PaymentWindow - " + e);
	} catch (NumberFormatException e) {
	    // entered value is no number
	    paymentBetrag.focus();
	}
	return valid;
    }

    private void addPayment() {
	if(validateInput()) {
	    if(container != null) {
		payment = new PaymentOrder(paymentText.getValue(), creatorBox.getComboBoxIDValue(), 
			requestBox.getComboBoxIDValue(), Double.parseDouble(paymentBetrag.getValue()), 
			LocalDate.now());
		
		container.addPayment(payment);
		
		// close Window
		PaymentWindow.this.close();
	    }
	}
	
    }
    
    private void updatePayment() {
	if(validateInput()) {
	    if(container != null) {
		try {
		    payment.setBetrag(Double.parseDouble(paymentBetrag.getValue()));
		    payment.setPaymentText(paymentText.getValue());
		    payment.setErstellerKontoId(creatorBox.getComboBoxIDValue());
		    payment.setSchuldnerKontoId(requestBox.getComboBoxIDValue());
		    
		    container.updatePayment(itemId, payment);
		    
		    PaymentWindow.this.close();
		    
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	}
    }

}
