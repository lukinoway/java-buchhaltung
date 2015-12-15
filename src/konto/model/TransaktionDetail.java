package konto.model;

import java.time.LocalDate;

public class TransaktionDetail {

    private final int detailId;
    private final int transaktionsId;
    private final int detailNr;
    private final Double detailBetrag;
    private final int detailType;
    private final String detailText;
    private final LocalDate detailCreationDate;
    private final boolean detailBillAvailable;

    /**
     * this constructor will be used when we write to DB
     * 
     * @param trd_nr
     * @param trd_betrag
     * @param trd_text
     * @param trd_type
     */
    public TransaktionDetail(int trd_nr, double trd_betrag, String trd_text, int trd_type) {
	this.detailNr = trd_nr;
	this.detailBetrag = trd_betrag;
	this.detailText = trd_text;
	this.detailType = trd_type;
	this.detailCreationDate = LocalDate.now();

	// set a dummy id
	this.detailId = 1;
	this.transaktionsId = 1;
	this.detailBillAvailable = false;
    }

    /**
     * This constructor will be used when we get data from DB
     * 
     * @param tr_id
     * @param trd_id
     * @param trd_nr
     * @param trd_betrag
     * @param trd_text
     * @param trd_type
     */
    public TransaktionDetail(int tr_id, int trd_id, int trd_nr, LocalDate trd_date, double trd_betrag, String trd_text,
	    int trd_type, boolean trd_billavailable) {
	this.detailNr = trd_nr;
	this.detailBetrag = trd_betrag;
	this.detailText = trd_text;
	this.detailType = trd_type;
	this.detailCreationDate = trd_date;
	this.detailId = trd_id;
	this.transaktionsId = tr_id;
	this.detailBillAvailable = trd_billavailable;
    }

    // transaktionsDetail_id handling
    public int getDetailId() {
	return detailId;
    }

    // transaktions_id handling
    public int getTransaktionsId() {
	return transaktionsId;
    }

    // transaktionsDetail_nr handling
    public int getDetailNr() {
	return detailNr;
    }

    // transaktionsDetail_type handling
    public int getDetailType() {
	return detailType;
    }

    // transaktionsDetail_betrag handling
    public Double getDetailBetrag() {
	return detailBetrag;
    }

    // transaktionsDetail_text handling
    public String getDetailText() {
	return detailText;
    }

    // transaktionsDetail_date handling
    public LocalDate getDetailCreationDate() {
	return detailCreationDate;
    }

    public boolean isBillAvailable() {
	return detailBillAvailable;
    }

}
