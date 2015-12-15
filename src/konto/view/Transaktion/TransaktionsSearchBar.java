package konto.view.Transaktion;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.swing.text.DateFormatter;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

public class TransaktionsSearchBar extends HorizontalLayout {

    String format = "yyyy-MM-dd";

    // GUI features
    Label nameLbl = new Label("SearchBar");
    TextField kontoNr = new TextField("Konto ID");
    DateField fromDate = new DateField("Von");
    DateField toDate = new DateField("Bis");
    public Button searchBtn = new Button("Search");

    public TransaktionsSearchBar() {
	// set date format to 2015-04-29
	fromDate.setDateFormat(format);
	toDate.setDateFormat(format);

	nameLbl.setStyleName("h2");

	this.addComponent(nameLbl);
	this.addComponent(kontoNr);
	this.addComponent(fromDate);
	this.addComponent(toDate);
	this.addComponent(searchBtn);
	this.setComponentAlignment(searchBtn, Alignment.BOTTOM_RIGHT);
    }

    public LocalDate getFromDate() {
	LocalDate returnDate;
	if (fromDate.getValue() == null) {
	    returnDate = LocalDate.parse("2010-01-01");
	} else {
	    returnDate = LocalDateTime.ofInstant(fromDate.getValue().toInstant(), ZoneId.systemDefault()).toLocalDate();
	}
	return returnDate;

    }

    public LocalDate getToDate() {
	LocalDate returnDate;
	if (toDate.getValue() == null) {
	    returnDate = LocalDate.now();
	} else {
	    returnDate = LocalDateTime.ofInstant(toDate.getValue().toInstant(), ZoneId.systemDefault()).toLocalDate();
	}
	return returnDate;
    }

}
