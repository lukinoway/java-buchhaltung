package konto.data.model;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Konto {

    private String kontonr;
    private String kontoname;
    public ArrayList<Transaktion> transaktion;

    Konto() {
	this(null, null);
    }

    public Konto(String knr, String kname) {
	this.kontonr = knr;
	this.kontoname = kname;
	this.transaktion = new ArrayList<Transaktion>();
    }

    public String getKontoNr() {
	return this.kontonr;
    }

    public String getKontoName() {
	return this.kontoname;
    }

    public void createTransaktion(String text, String Datum1, String Datum2, String betrag, String Waehrung)
	    throws NoSuchAlgorithmException {
	// convert date to LocalDate
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	LocalDate trdate = LocalDate.parse(Datum1, formatter);

	this.transaktion.add(new Transaktion(trdate, Double.parseDouble(betrag), text));
    }

    public void setKontoNr(String knr) {
	this.kontonr = knr;

    }

}
