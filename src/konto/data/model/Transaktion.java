package konto.data.model;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

public class Transaktion {

    private int transaktionsId;
    private Double transaktionsBetrag;
    private String transaktionsText;
    private String transaktionsHash;
    private LocalDate transaktionsDate;
    private int kontoId;
    private int typeId;

    /**
     * this will be used to read csv and write to DB / tr_id will be created in
     * DB
     * 
     * @param tr_date
     * @param tr_betrag
     * @param tr_text
     * @throws NoSuchAlgorithmException
     */
    public Transaktion(LocalDate tr_date, Double tr_betrag, String tr_text, int kontoId, int typeId)
	    throws NoSuchAlgorithmException {

	// set values
	this.transaktionsDate = tr_date;
	this.transaktionsBetrag = tr_betrag;
	this.transaktionsText = tr_text;
	this.kontoId = kontoId;
	this.typeId = typeId;

	// dummy_id
	this.transaktionsId = 0;

	// create hash
	createTransaktionsHash();
    }

    /**
     * this constructor will be used when we get data from our DB
     * 
     * @param tr_id
     * @param tr_date
     * @param tr_betrag
     * @param tr_text
     * @param tr_hash
     */
    public Transaktion(int tr_id, LocalDate tr_date, Double tr_betrag, String tr_text, String tr_hash, int kontoId,
	    int typeId) {

	// set values
	this.transaktionsDate = tr_date;
	this.transaktionsBetrag = tr_betrag;
	this.transaktionsText = tr_text;
	this.transaktionsId = tr_id;
	this.transaktionsHash = tr_hash;
	this.kontoId = kontoId;
	this.typeId = typeId;
    }

    public void setTransaktionsBetrag(Double transaktionsBetrag) {
	this.transaktionsBetrag = transaktionsBetrag;
    }

    public void setTransaktionsText(String transaktionsText) {
	this.transaktionsText = transaktionsText;
    }

    public void setTransaktionsDate(LocalDate transaktionsDate) {
	this.transaktionsDate = transaktionsDate;
    }

    public void setKontoId(int kontoId) {
	this.kontoId = kontoId;
    }

    public void setTypeId(int typeId) {
	this.typeId = typeId;
    }

    public void setTransaktionsId(int transaktionsId) {
	this.transaktionsId = transaktionsId;
    }

    // transaktiosns_id handling
    public int getTransaktionsId() {
	return transaktionsId;
    }

    // transaktions_betrag handling
    public Double getTransaktionsBetrag() {
	return transaktionsBetrag;
    }

    // transaktions_text handling
    public String getTransaktionsText() {
	return transaktionsText;
    }

    // transaktions date handling
    public LocalDate getTransaktionsDate() {
	return transaktionsDate;
    }

    public String getTransaktionsHash() {
	return transaktionsHash;
    }

    /**
     * Function to create a hash of input information
     * 
     * @param in_string
     * @throws NoSuchAlgorithmException
     */
    public void createTransaktionsHash() throws NoSuchAlgorithmException {
	String in_string = this.transaktionsText + this.transaktionsDate.toString() + this.transaktionsBetrag;
	try {
	    MessageDigest m = MessageDigest.getInstance("MD5");
	    m.reset();
	    m.update(in_string.getBytes());
	    byte[] digest = m.digest();
	    BigInteger bigInt = new BigInteger(1, digest);
	    String hashtext = bigInt.toString(16);
	    // Now we need to zero pad it if you actually want the full 32
	    // chars.
	    while (hashtext.length() < 32) {
		hashtext = "0" + hashtext;
	    }
	    this.transaktionsHash = hashtext;
	} catch (NoSuchAlgorithmException e) {
	    System.out.println("createTransaktionsHash - hier lief was schief: NoSuchAlgorithmException");
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public int getKontoId() {
	return kontoId;
    }

    public int getTypeId() {
	return typeId;
    }

}
