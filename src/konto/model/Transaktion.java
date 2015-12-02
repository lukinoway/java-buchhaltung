package konto.model;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Transaktion {

    private final int transaktions_id;
    private final Double transaktions_betrag;
    private final String transaktions_text;
    private String transaktions_hash;
    private final LocalDate transaktions_date;

    public ArrayList<TransaktionDetail> transaktionDetail;

    public Transaktion() throws NoSuchAlgorithmException {
        this(null, null, null);
    }

    /**
     * this will be used to read csv and write to DB / tr_id will be created in DB
     * @param tr_date
     * @param tr_betrag
     * @param tr_text
     * @throws NoSuchAlgorithmException
     */
	public Transaktion(LocalDate tr_date, Double tr_betrag, String tr_text) throws NoSuchAlgorithmException {

		// set values
		this.transaktions_date = tr_date;
		this.transaktions_betrag = tr_betrag;
		this.transaktions_text = tr_text;

		// dummy_id
		this.transaktions_id = 1;

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
		this.transaktions_date = tr_date;
		this.transaktions_betrag = tr_betrag;
		this.transaktions_text = tr_text;
		this.transaktions_id = tr_id;
		this.transaktions_hash = tr_hash;
		transaktionDetail = new ArrayList<TransaktionDetail>();
	}

	// transaktiosns_id handling
	public int getTransaktionsId() {
		return transaktions_id;
	}

    // transaktions_betrag handling
	public Double getTransaktionsBetrag() {
		return transaktions_betrag;
	}

	// transaktions_text handling
	public String getTransaktionsText() {
		return transaktions_text;
	}

	// transaktions date handling
	public LocalDate getTransaktionsDate() {
		return transaktions_date;
	}
	
	public String getTransaktionsHash() {
		return transaktions_hash;
	}


	/**
	 * Function to create a hash of input information
	 * @param in_string
	 * @throws NoSuchAlgorithmException
	 */
	public void createTransaktionsHash(String in_string) throws NoSuchAlgorithmException {
		try {
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
		this.transaktions_hash = hashtext;
		} catch(NoSuchAlgorithmException e) {
			System.out.println("createTransaktionsHash - hier lief was schief: NoSuchAlgorithmException");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}



}
