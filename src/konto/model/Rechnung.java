package konto.model;

import java.time.LocalDate;

public class Rechnung {
	
	private final int rechnungsId;
	private final LocalDate rechnungsDate;
	private final String rechnungsText;
	
	public Rechnung (int billId, LocalDate billDate, String billText) {
		 this.rechnungsId = billId;
		 this.rechnungsDate = billDate;
		 this.rechnungsText = billText;
	}
	
	public int getRechnungsId() {
		return this.rechnungsId;
	}
	
	public LocalDate getRechnungsDatum() {
		return this.rechnungsDate;
	}
	
	public String getRechnungsText() {
		return this.rechnungsText;
	}
}
