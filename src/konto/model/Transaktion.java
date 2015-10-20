package konto.model;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Transaktion {


    private final IntegerProperty transaktions_id;
    private final DoubleProperty transaktions_betrag;
    private final StringProperty transaktions_text;
    private StringProperty transaktions_hash;
    private final ObjectProperty<LocalDate> transaktions_date;

    public ArrayList<TransaktionDetail> transaktionDetail;

    public Transaktion() throws NoSuchAlgorithmException {
        this(null, null, null);
    }

    /**
     * this will be used to read cvs and write to DB / tr_id will be created in DB
     * @param tr_date
     * @param tr_betrag
     * @param tr_text
     * @throws NoSuchAlgorithmException
     */
	public Transaktion(LocalDate tr_date, Double tr_betrag, String tr_text) throws NoSuchAlgorithmException {

		// set values
		this.transaktions_date = new SimpleObjectProperty<LocalDate>(LocalDate.of(2015, 2, 21));
		this.transaktions_betrag = new SimpleDoubleProperty(tr_betrag);
		this.transaktions_text = new SimpleStringProperty(tr_text);

		// dummy_id
		this.transaktions_id = new SimpleIntegerProperty(1);

		// create hash
		createTransaktionsHash(tr_text + tr_date.toString() + tr_betrag);

		// create Detail Array
		transaktionDetail = new ArrayList<TransaktionDetail>();
		transaktionDetail.add(new TransaktionDetail(1, tr_betrag, tr_text, 1));
	}


	/**
	 * this constructor will be used when we get data from our DB
	 * @param tr_id
	 * @param tr_date
	 * @param tr_betrag
	 * @param tr_text
	 * @param tr_hash
	 */
	public Transaktion(int tr_id, LocalDate tr_date, Double tr_betrag, String tr_text, String tr_hash) {

		// set values
		this.transaktions_date = new SimpleObjectProperty<LocalDate>(LocalDate.of(2015, 2, 21));
		this.transaktions_betrag = new SimpleDoubleProperty(tr_betrag);
		this.transaktions_text = new SimpleStringProperty(tr_text);
		this.transaktions_id = new SimpleIntegerProperty(tr_id);
		this.transaktions_hash = new SimpleStringProperty(tr_hash);
		transaktionDetail = new ArrayList<TransaktionDetail>();
	}

	// transaktiosns_id handling
	public int getTransaktions_id() {
		return transaktions_id.get();
	}

	public void setTransaktions_id(int tr_id) {
		this.transaktions_id.set(tr_id);
	}

	public IntegerProperty TransaktionsIdProperty() {
		return transaktions_id;
	}

    // transaktions_betrag handling
	public Double getTransaktions_betrag() {
		return transaktions_betrag.get();
	}

	public void setTransaktions_betrag(double tr_betrag) {
		this.transaktions_betrag.set(tr_betrag);
	}

	public DoubleProperty TransaktionsBetragProperty() {
		return transaktions_betrag;
	}

	// transaktions_text handling
	public String getTransaktions_text() {
		return transaktions_text.get();
	}

	public void setTransaktions_text(String tr_text) {
		this.transaktions_text.set(tr_text);
	}

	public StringProperty TransaktionsTextProperty() {
		return transaktions_text;
	}

	// transaktions date handling
	public LocalDate getTransaktions_date() {
		return transaktions_date.get();
	}

	public void setTransaktions_date(LocalDate tr_date) {
		this.transaktions_date.set(tr_date);
	}

	public ObjectProperty<LocalDate> TransaktionsDateProperty() {
		return transaktions_date;
	}

	// create MD5 hash
	public void createTransaktionsHash(String in_string) throws NoSuchAlgorithmException {
		MessageDigest m = MessageDigest.getInstance("MD5");
		m.reset();
		m.update(in_string.getBytes());
		byte[] digest = m.digest();
		BigInteger bigInt = new BigInteger(1,digest);
		String hashtext = bigInt.toString(16);
		// Now we need to zero pad it if you actually want the full 32 chars.
		while(hashtext.length() < 32 ){
			  hashtext = "0"+hashtext;
			}
		this.transaktions_hash = new SimpleStringProperty(hashtext);
	}

	public String getTransaktions_hash() {
		return transaktions_hash.get();
	}

}
