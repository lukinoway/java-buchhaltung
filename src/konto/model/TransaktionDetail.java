package konto.model;

import java.time.LocalDate;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TransaktionDetail {

    private final IntegerProperty transaktionsDetail_id;
    private final IntegerProperty transaktionsDetail_nr;
    private final DoubleProperty transaktionsDetail_betrag;
    private final IntegerProperty transaktionsDetail_type;
    private final StringProperty transaktionsDetail_text;
    private final ObjectProperty<LocalDate> transaktionsDetail_creationDate;


    /**
     * this constructor will be used when we write to DB
     * @param trd_nr
     * @param trd_betrag
     * @param trd_text
     * @param trd_type
     */
    public TransaktionDetail(int trd_nr, double trd_betrag, String trd_text, int trd_type) {
    	this.transaktionsDetail_nr = new SimpleIntegerProperty(trd_nr);
    	this.transaktionsDetail_betrag = new SimpleDoubleProperty(trd_betrag);
    	this.transaktionsDetail_text = new SimpleStringProperty(trd_text);
    	this.transaktionsDetail_type = new SimpleIntegerProperty(trd_type);
    	this.transaktionsDetail_creationDate = new SimpleObjectProperty<LocalDate>(LocalDate.now());

    	// set a dummy id
    	this.transaktionsDetail_id = new SimpleIntegerProperty(1);

    }

    /**
     * This constructor will be used when we get data from DB
     * @param tr_id
     * @param trd_nr
     * @param trd_betrag
     * @param trd_text
     * @param trd_type
     */
    public TransaktionDetail(int tr_id, int trd_nr, LocalDate trd_date, double trd_betrag, String trd_text, int trd_type) {
    	this.transaktionsDetail_nr = new SimpleIntegerProperty(trd_nr);
    	this.transaktionsDetail_betrag = new SimpleDoubleProperty(trd_betrag);
    	this.transaktionsDetail_text = new SimpleStringProperty(trd_text);
    	this.transaktionsDetail_type = new SimpleIntegerProperty(trd_type);
    	this.transaktionsDetail_creationDate = new SimpleObjectProperty<LocalDate>(trd_date);
    	this.transaktionsDetail_id = new SimpleIntegerProperty(tr_id);
    }


	// transaktiosnsDetail_id handling
	public int getTransaktionsDetail_id() {
		return transaktionsDetail_id.get();
	}

	public void setTransaktionsDetail_id(int trd_id) {
		this.transaktionsDetail_id.set(trd_id);
	}

	public IntegerProperty TransaktionsDetailIdProperty() {
		return transaktionsDetail_id;
	}

	// transaktiosnsDetail_id handling
	public int getTransaktionsDetail_nr() {
		return transaktionsDetail_nr.get();
	}

	public void setTransaktionsDetail_nr(int trd_nr) {
		this.transaktionsDetail_nr.set(trd_nr);
	}

	public IntegerProperty TransaktionsDetailNrProperty() {
		return transaktionsDetail_nr;
	}

	// transaktiosnsDetail_id handling
	public int getTransaktionsDetail_type() {
		return transaktionsDetail_type.get();
	}

	public void setTransaktionsDetail_type(int trd_type) {
		this.transaktionsDetail_type.set(trd_type);
	}

	public IntegerProperty TransaktionsDetailTypeProperty() {
		return transaktionsDetail_type;
	}

    // transaktionsDetail_betrag handling
	public Double getTransaktionsDetail_betrag() {
		return transaktionsDetail_betrag.get();
	}

	public void setTransaktionsDetail_betrag(double trd_betrag) {
		this.transaktionsDetail_betrag.set(trd_betrag);
	}

	public DoubleProperty TransaktionsDetailBetragProperty() {
		return transaktionsDetail_betrag;
	}

	// transaktionsDetail_text handling
	public String getTransaktionsDetail_text() {
		return transaktionsDetail_text.get();
	}

	public void setTransaktionsDetail_text(String trd_text) {
		this.transaktionsDetail_text.set(trd_text);
	}

	public StringProperty TransaktionsDetailTextProperty() {
		return transaktionsDetail_text;
	}

	// transaktionsDetail date handling
	public LocalDate getTransaktionsDetail_creationDate() {
		return transaktionsDetail_creationDate.get();
	}

	public void setTransaktionsDetail_creationDate(LocalDate trd_date) {
		this.transaktionsDetail_creationDate.set(trd_date);
	}

	public ObjectProperty<LocalDate> TransaktionsDetailCreationDateProperty() {
		return transaktionsDetail_creationDate;
	}
}
