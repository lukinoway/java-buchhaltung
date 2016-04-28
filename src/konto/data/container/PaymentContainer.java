package konto.data.container;

import java.time.LocalDate;
import java.util.ArrayList;

import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;

import konto.data.DBUtil.IPayment;
import konto.data.DBUtil.ITransaktion;
import konto.data.DBUtil.PaymentDBUtil;
import konto.data.DBUtil.TransaktionDBUtil;
import konto.data.model.PaymentOrder;
import konto.data.model.PaymentStatus;
import konto.data.model.Transaktion;

public class PaymentContainer extends IndexedContainer {

    private static final long serialVersionUID = 1L;
    IPayment paymentUtil = new PaymentDBUtil();
    
    @SuppressWarnings("unchecked")
    public PaymentContainer(ArrayList<PaymentOrder> paymentList) {
	this.addContainerProperty("ID", Integer.class, null);
	this.addContainerProperty("creatorKnt", Integer.class, null);
	this.addContainerProperty("borrowerKnt", Integer.class, null);
	this.addContainerProperty("Text", String.class, null);
	this.addContainerProperty("Betrag", Double.class, null);
	this.addContainerProperty("Datum", LocalDate.class, null);
	this.addContainerProperty("Status", PaymentStatus.class, null);
	
	for(PaymentOrder payment : paymentList) {
	    Object id = addItem();
	    Item item = getItem(id);
	    if (item != null) {
		item.getItemProperty("ID").setValue(payment.getPaymentId());
		item.getItemProperty("creatorKnt").setValue(payment.getErstellerKontoId());
		item.getItemProperty("borrowerKnt").setValue(payment.getSchuldnerKontoId());
		item.getItemProperty("Text").setValue(payment.getPaymentText());
		item.getItemProperty("Betrag").setValue(payment.getBetrag());
		item.getItemProperty("Datum").setValue(payment.getDate());
		item.getItemProperty("Status").setValue(payment.getStatus());
	    }
	}
	
    }
    
    @SuppressWarnings("unchecked")
    public void addPayment(PaymentOrder payment) {
	
	paymentUtil.createPayment(payment);
	
	// add to List
	Object id = addItem();
	Item item = getItem(id);
	    if (item != null) {
		item.getItemProperty("ID").setValue(payment.getPaymentId());
		item.getItemProperty("creatorKnt").setValue(payment.getErstellerKontoId());
		item.getItemProperty("borrowerKnt").setValue(payment.getSchuldnerKontoId());
		item.getItemProperty("Text").setValue(payment.getPaymentText());
		item.getItemProperty("Betrag").setValue(payment.getBetrag());
		item.getItemProperty("Datum").setValue(payment.getDate());
		item.getItemProperty("Status").setValue(payment.getStatus());
	    }
	    
    }
    
    @SuppressWarnings("unchecked")
    public void addPaymentLocal(PaymentOrder payment) {
	// add to List
	Object id = addItem();
	Item item = getItem(id);
	    if (item != null) {
		item.getItemProperty("ID").setValue(payment.getPaymentId());
		item.getItemProperty("creatorKnt").setValue(payment.getErstellerKontoId());
		item.getItemProperty("borrowerKnt").setValue(payment.getSchuldnerKontoId());
		item.getItemProperty("Text").setValue(payment.getPaymentText());
		item.getItemProperty("Betrag").setValue(payment.getBetrag());
		item.getItemProperty("Datum").setValue(payment.getDate());
		item.getItemProperty("Status").setValue(payment.getStatus());
	    }
	    
    }
    
    @SuppressWarnings("unchecked")
    public void updatePayment(Object itemId, PaymentOrder payment) {
	Item item = getItem(itemId);
	if (item != null) {
	    paymentUtil.updatePayment(payment);

	    item.getItemProperty("ID").setValue(payment.getPaymentId());
	    item.getItemProperty("creatorKnt").setValue(payment.getErstellerKontoId());
	    item.getItemProperty("borrowerKnt").setValue(payment.getSchuldnerKontoId());
	    item.getItemProperty("Text").setValue(payment.getPaymentText());
	    item.getItemProperty("Betrag").setValue(payment.getBetrag());
	    item.getItemProperty("Datum").setValue(payment.getDate());
	    item.getItemProperty("Status").setValue(payment.getStatus());
	}
    }
    
    
    public PaymentOrder buildPayment(Item item) {
	return new PaymentOrder(
		(Integer) item.getItemProperty("ID").getValue(), 
		(String) item.getItemProperty("Text").getValue(), 
		(Integer) item.getItemProperty("creatorKnt").getValue(), 
		(Integer) item.getItemProperty("borrowerKnt").getValue(), 
		(Double) item.getItemProperty("Betrag").getValue(), 
		(LocalDate) item.getItemProperty("Datum").getValue(),
		(PaymentStatus) item.getItemProperty("Status").getValue()
		);
    }
    
    @SuppressWarnings("unchecked")
    public void onPayment(Object itemId, PaymentOrder payment) {
	
	payment.setStatus(PaymentStatus.BEZAHLT);
	paymentUtil.updatePaymentStatus(payment);
	Item item = getItem(itemId);
	if (item != null) {
	    item.getItemProperty("Status").setValue(payment.getStatus());
	}
	
	// change status to "bezahlt"

	ITransaktion transaktionUtil = new TransaktionDBUtil();
	try {
	    // first create Transaktion for creator
	    Transaktion transaktion = new Transaktion(LocalDate.now(), payment.getBetrag(), 
		    			payment.getPaymentText(), payment.getErstellerKontoId(), 0);
	    transaktionUtil.createTransaktion(transaktion);
	    
	    // now for schuldner
	    transaktion.setKontoId(payment.getSchuldnerKontoId());
	    transaktion.setTransaktionsBetrag(payment.getBetrag()*-1);
	    transaktion.createTransaktionsHash();
	    
	    transaktionUtil.createTransaktion(transaktion);
	    
	    
	} catch (Exception e) {
	    e.printStackTrace();
	}
	    
	
    }

    
}
