package konto.data.model;

import java.io.Serializable;
import java.time.LocalDate;

public class PaymentOrder implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private int paymentId;
    private int erstellerKontoId;
    private int schuldnerKontoId;
    private String paymentText;
    private double betrag;
    private LocalDate date;
    private PaymentStatus status;

    /**
     * Payment order Class - enter creator and konto from other person 
     * @param text
     * @param erstellerId
     * @param schuldnerId
     * @param betrag
     * @param date
     */
    public PaymentOrder(String text, int erstellerId, int schuldnerId, double betrag, LocalDate date) {
	this.paymentText = text;
	this.erstellerKontoId = erstellerId;
	this.schuldnerKontoId = schuldnerId;
	this.betrag = betrag;
	this.date = date;
	this.setStatus(PaymentStatus.NEU);
	
	this.paymentId = 0;
    }
    
    public PaymentOrder(int paymentId,String text, int erstellerId, int schuldnerId, double betrag, LocalDate date) {
	this.paymentText = text;
	this.erstellerKontoId = erstellerId;
	this.schuldnerKontoId = schuldnerId;
	this.betrag = betrag;
	this.date = date;
	this.status = PaymentStatus.NEU;
	this.paymentId = paymentId;
    }
    
    public PaymentOrder(int paymentId,String text, int erstellerId, int schuldnerId, double betrag, LocalDate date, PaymentStatus status) {
	this.paymentText = text;
	this.erstellerKontoId = erstellerId;
	this.schuldnerKontoId = schuldnerId;
	this.betrag = betrag;
	this.date = date;
	this.status = status;
	
	
	this.paymentId = paymentId;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getErstellerKontoId() {
        return erstellerKontoId;
    }

    public void setErstellerKontoId(int erstellerKontoId) {
        this.erstellerKontoId = erstellerKontoId;
    }

    public int getSchuldnerKontoId() {
        return schuldnerKontoId;
    }

    public void setSchuldnerKontoId(int schuldnerKontoId) {
        this.schuldnerKontoId = schuldnerKontoId;
    }

    public String getPaymentText() {
        return paymentText;
    }

    public void setPaymentText(String paymentText) {
        this.paymentText = paymentText;
    }

    public double getBetrag() {
        return betrag;
    }

    public void setBetrag(double betrag) {
        this.betrag = betrag;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public PaymentStatus getStatus() {
	return status;
    }

    public void setStatus(PaymentStatus status) {
	this.status = status;
    }
    
}
