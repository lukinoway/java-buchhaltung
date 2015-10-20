package konto.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import konto.model.Transaktion;
import konto.view.TransaktionOverviewController;

public class TransaktionDBUtil extends DBCommunicator {


	private Connection connect = null;
	private Statement statement = null;
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
			
			// first of all clear old data
			transaktionData.removeAll(this.transaktion);
			troc.TransaktionsTable.setItems(getTransaktionData());
			
			this.resultSet = getData("db_transaktion", 
								"transaktions_id, transaktions_datum, transaktions_betrag, transaktions_text, transaktions_hash", 
								"where transaktions_datum between \"" + startDate.toString() + "\" and \"" + endDate.toString() + "\"");
			
			// perpare table
		    while (this.resultSet.next()) {

			      int tr_id = this.resultSet.getInt("transaktions_id");
			      String tr_date = this.resultSet.getString("transaktions_datum");
			      double betrag = this.resultSet.getDouble("transaktions_betrag");
			      String tr_text = this.resultSet.getString("transaktions_text");
			      String tr_hash = this.resultSet.getString("transaktions_hash");
			      
			      transaktionData.add(new Transaktion(tr_id, LocalDate.parse(tr_date), betrag, tr_text, tr_hash));
					
			      troc.trIDColumn.setCellValueFactory(cellDate -> cellDate.getValue().TransaktionsIdProperty());
			      troc.trDateColumn.setCellValueFactory(cellDate -> cellDate.getValue().TransaktionsDateProperty());
			      troc.trBetragColumn.setCellValueFactory(cellDate -> cellDate.getValue().TransaktionsBetragProperty());
			      troc.trTextColumn.setCellValueFactory(cellDate -> cellDate.getValue().TransaktionsTextProperty());
		    }
		    
		    //load data to table
		    troc.TransaktionsTable.setItems(getTransaktionData());
		} catch (NullPointerException e) {
			System.out.println("Es konnten keine Daten gefunden werden");
		} finally {
		    close();
		}
	}
	
	public void getTransaktionFromDB(LocalDate startDate, LocalDate endDate, String type) throws Exception {
		try {
			System.out.println("Start Date: " + startDate.toString());
			System.out.println("End Date:   " + endDate.toString());
			System.out.println("Type: " + type);
			
			// first of all clear old data
			transaktionData.removeAll(this.transaktion);
			troc.TransaktionsTable.setItems(getTransaktionData());
			
			this.resultSet = getData("db_transaktion", 
								"transaktions_id, transaktions_datum, transaktions_betrag, transaktions_text, transaktions_hash", 
								"where transaktions_datum between \"" + startDate.toString() + "\" and \"" + endDate.toString() + "\" and transaktions_typ_id =" + type);
			
			// perpare table
		    while (this.resultSet.next()) {

			      int tr_id = this.resultSet.getInt("transaktions_id");
			      String tr_date = this.resultSet.getString("transaktions_datum");
			      double betrag = this.resultSet.getDouble("transaktions_betrag");
			      String tr_text = this.resultSet.getString("transaktions_text");
			      String tr_hash = this.resultSet.getString("transaktions_hash");
			      
			      transaktionData.add(new Transaktion(tr_id, LocalDate.parse(tr_date), betrag, tr_text, tr_hash));
					
			      troc.trIDColumn.setCellValueFactory(cellDate -> cellDate.getValue().TransaktionsIdProperty());
			      troc.trDateColumn.setCellValueFactory(cellDate -> cellDate.getValue().TransaktionsDateProperty());
			      troc.trBetragColumn.setCellValueFactory(cellDate -> cellDate.getValue().TransaktionsBetragProperty());
			      troc.trTextColumn.setCellValueFactory(cellDate -> cellDate.getValue().TransaktionsTextProperty());
		    }
		    
		    //load data to table
		    troc.TransaktionsTable.setItems(getTransaktionData());
		} catch (NullPointerException e) {
			System.out.println("Es konnten keine Daten gefunden werden");
		} finally {
		    close();
		}
	}
	
	public void getTransaktionFromDB(String type) throws Exception {
		try {
			System.out.println("Type: " + type);
			
			// first of all clear old data
			transaktionData.removeAll(this.transaktion);
			troc.TransaktionsTable.setItems(getTransaktionData());
			
			this.resultSet = getData("db_transaktion", 
								"transaktions_id, transaktions_datum, transaktions_betrag, transaktions_text, transaktions_hash", 
								"where transaktions_typ_id =" + type);
			
			// perpare table
		    while (this.resultSet.next()) {

			      int tr_id = this.resultSet.getInt("transaktions_id");
			      String tr_date = this.resultSet.getString("transaktions_datum");
			      double betrag = this.resultSet.getDouble("transaktions_betrag");
			      String tr_text = this.resultSet.getString("transaktions_text");
			      String tr_hash = this.resultSet.getString("transaktions_hash");
			      
			      transaktionData.add(new Transaktion(tr_id, LocalDate.parse(tr_date), betrag, tr_text, tr_hash));
					
			      troc.trIDColumn.setCellValueFactory(cellDate -> cellDate.getValue().TransaktionsIdProperty());
			      troc.trDateColumn.setCellValueFactory(cellDate -> cellDate.getValue().TransaktionsDateProperty());
			      troc.trBetragColumn.setCellValueFactory(cellDate -> cellDate.getValue().TransaktionsBetragProperty());
			      troc.trTextColumn.setCellValueFactory(cellDate -> cellDate.getValue().TransaktionsTextProperty());
		    }
		    
		    //load data to table
		    troc.TransaktionsTable.setItems(getTransaktionData());
		} catch (NullPointerException e) {
			System.out.println("Es konnten keine Daten gefunden werden");
		} finally {
		    close();
		}
	}
	
	// You need to close the resultSet
	private void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (this.resultSet != null) {
				this.resultSet.close();
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
