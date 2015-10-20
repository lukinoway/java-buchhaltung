package konto.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import konto.MainApp;
import konto.model.Transaktion;
import konto.model.TransaktionDetail;
import konto.view.TransaktionOverviewController;

public class TransaktionDBUtil extends DBCommunicator {

	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	private Transaktion transaktion;
	
	private TransaktionOverviewController troc;
	private ObservableList<Transaktion> transaktionData = FXCollections.observableArrayList();
	
	/**
	 * Insert new transaktion in DB
	 * @param transaktion
	 */
	public TransaktionDBUtil(){
		super();
	}
	
	public void insertTransaktion(Transaktion transaktion) {
		// this will be used for csv parse and insert
	}

	/**
	 * with this Function we get Transaktion that are between start and end Date
	 * @param startDate
	 * @param endDate
	 * @throws Exception 
	 */
	public void getTransaktionFromDB(LocalDate startDate, LocalDate endDate) throws Exception {

		try {
			System.out.println("Start Date: " + startDate.toString());
			System.out.println("End Date:   " + endDate.toString());
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			
			// first of all clear old data
			transaktionData.removeAll(this.transaktion);
			troc.TransaktionsTable.setItems(getTransaktionData());
			
			getData("db_transaktion", 
					"transaktions_id, transaktions_datum, transaktions_betrag, transaktions_text, transaktions_hash", 
					" where transaktions_datum between \"" + startDate.toString() + "\" and \"" + endDate.toString() + "\"");
			
			System.out.println("where transaktions_datum between \"" + startDate.toString() + "\" and \"" + endDate.toString() + "\"");
			
			int i = 0;
		    while (resultSet.next()) {
			      // It is possible to get the columns via name
			      // also possible to get the columns via the column number
			      // which starts at 1
			      // e.g. resultSet.getSTring(2);
			      int tr_id = resultSet.getInt("transaktions_id");
			      String tr_date = resultSet.getString("transaktions_datum");
			      double betrag = resultSet.getDouble("transaktions_betrag");
			      String tr_text = resultSet.getString("transaktions_text");
			      String tr_hash = resultSet.getString("transaktions_hash");
			      
			      transaktionData.add(new Transaktion(tr_id, LocalDate.parse(tr_date), betrag, tr_text, tr_hash));
					
			      troc.trIDColumn.setCellValueFactory(cellDate -> cellDate.getValue().TransaktionsIdProperty());
			      troc.trDateColumn.setCellValueFactory(cellDate -> cellDate.getValue().TransaktionsDateProperty());
			      troc.trBetragColumn.setCellValueFactory(cellDate -> cellDate.getValue().TransaktionsBetragProperty());
			      troc.trTextColumn.setCellValueFactory(cellDate -> cellDate.getValue().TransaktionsTextProperty());
			      
			      System.out.println("got "+ i +". transaction, TIME: " + LocalTime.now() + "tr_text");
			      //text.add(String.valueOf(monat));
			      
			      //String text, String Datum1, String betrag, int tr_id
			      //kn.transaktionen[i] = new Transaktion(tr_text,tr_date,String.valueOf(betrag), tr_id);
			      
			      i++;
		    }
		    //load data to table
		    troc.TransaktionsTable.setItems(getTransaktionData());
		} catch (NullPointerException e) {
			System.out.println("Es konnten keine Daten gefunden werden");
		} finally {
		    close();
		}
	}
	
	public void getTransaktionFromDB(LocalDate startDate, LocalDate endDate, String type) {
		// get transaktions from DB
		// select * from db_transaktion
	}
	
	public void getTransaktionFromDB(String type) {
		// get transaktions from DB
		// select * from db_transaktion
	}
	
	// You need to close the resultSet
	private void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
    } 
	
    public ObservableList<Transaktion> getTransaktionData() {
        return transaktionData;
    }
	
    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setController(TransaktionOverviewController troc) {
        this.troc = troc;
    }

}
