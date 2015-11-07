package konto.DBUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import konto.model.Rechnung;
import konto.model.Transaktion;
import konto.view.TransaktionOverviewController;

public class RechnungsDBUtil extends DBCommunicator{
	
	// main stuff
	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	
	private Rechnung rechnung;
	private TransaktionOverviewController troc;
	private ObservableList<Rechnung> rechnungsData = FXCollections.observableArrayList();
	
	public RechnungsDBUtil() {
		super();
	}
	
	/**
	 * Load unknown Bills to view
	 */
	public void loadRechnungsPool() {
		try {
			// first of all clear data if there is any
			rechnungsData.removeAll(this.rechnung);
			troc.RechnungsPoolTable.setItems(getRechnungsData());
		
			this.resultSet = getData("db_transaktion_rechnung",
									 "transaktions_anhang_id, created",
									 "where transaktions_detail_id IS null and transaktions_id IS null");
			
			// fill table
			while(this.resultSet.next()) {
				int billId = this.resultSet.getInt(1);
				String billDate = this.resultSet.getString(2);
				
				rechnungsData.add(new Rechnung(billId, LocalDate.parse(billDate)));
				
				troc.rechnungsId.setCellValueFactory(cellDate -> cellDate.getValue().RechnungsIdProperty());
				troc.rechnungsDatum.setCellValueFactory(cellDate -> cellDate.getValue().RechnungsDatumProperty());
			}
			troc.RechnungsPoolTable.setItems(getRechnungsData());
			
		} catch (NullPointerException e) {
			System.out.println("keine Daten im Pool vorhanden!");
		} catch (Exception e) {
			System.out.println("Hier lief was schief - loadRechnungsPool");
			e.printStackTrace();
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
	
    public ObservableList<Rechnung> getRechnungsData() {
        return rechnungsData;
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
