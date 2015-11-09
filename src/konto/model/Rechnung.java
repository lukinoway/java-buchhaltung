package konto.model;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Rechnung {
	
	private final IntegerProperty rechnungs_id;
	private final ObjectProperty<LocalDate> rechnungs_datum;
	private final StringProperty rechnungs_text;
	
	public Rechnung (int billId, LocalDate billDate, String billText) {
		 this.rechnungs_id = new SimpleIntegerProperty(billId);
		 this.rechnungs_datum = new SimpleObjectProperty<LocalDate>(billDate);
		 this.rechnungs_text = new SimpleStringProperty(billText);
	}
	
	public void setRechnungsId (int billId) {
		this.rechnungs_id.set(billId);
	}
	
	public int getRechnungsId() {
		return this.rechnungs_id.get();
	}
	
	public IntegerProperty RechnungsIdProperty() {
		return this.rechnungs_id;
	}
	
	public void setRechnungsDatum (LocalDate billDate) {
		this.rechnungs_datum.set(billDate);
	}
	
	public LocalDate getRechnungsDatum() {
		return this.rechnungs_datum.get();
	}
	
	public ObjectProperty<LocalDate> RechnungsDatumProperty() {
		return this.rechnungs_datum;
	}
	
	public void setRechnungsText(String billText) {
		this.rechnungs_text.set(billText);
	}
	
	public String getRechnungsText() {
		return this.rechnungs_text.get();
	}
	
	public StringProperty RechnungsTextProperty() {
		return this.rechnungs_text;
	}
}
