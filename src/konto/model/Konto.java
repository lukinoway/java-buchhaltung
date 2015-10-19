package konto.model;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Konto {

	private StringProperty kontonr;
	private StringProperty kontoname;
	public ArrayList<Transaktion> transaktion;

	Konto() {
		this(null, null);
	}

	public Konto(String knr, String kname) {
		this.kontonr = new SimpleStringProperty(knr);
		this.kontoname = new SimpleStringProperty(kname);
		this.transaktion = new ArrayList<Transaktion>();
	}

	// konto nr handling
	public String getKontoNr() {
		return kontonr.get();
	}

	public void setKontoNr(String knr) {
		this.kontonr.set(knr);
	}

	public StringProperty KontoNrProperty() {
		return kontonr;
	}

	// konto name handling
	public String getKontoName() {
		return kontonr.get();
	}

	public void setKontoName(String kname) {
		this.kontoname.set(kname);
	}

	public StringProperty KontoNameProperty() {
		return kontoname;
	}

	public void createTransaktion(String text, String Datum1, String Datum2, String betrag, String Waehrung) throws NoSuchAlgorithmException {
		// convert date to LocalDate
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		LocalDate trdate = LocalDate.parse(Datum1, formatter);
		
		this.transaktion.add(new Transaktion(trdate, Double.parseDouble(betrag), text));
	}

}
