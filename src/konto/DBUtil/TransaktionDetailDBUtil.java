package konto.DBUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import konto.model.Transaktion;
import konto.model.TransaktionDetail;
import konto.view.TransaktionDetailOverviewController;
import konto.view.TransaktionOverviewController;

public class TransaktionDetailDBUtil extends DBCommunicator {

	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	
	private TransaktionDetail transaktionDetail;
	
	private TransaktionDetailOverviewController trdoc;
	private ObservableList<TransaktionDetail> transaktionDetailData = FXCollections.observableArrayList();
	
	// basic constructor
	public TransaktionDetailDBUtil() {
		super();
	}
	
    public ObservableList<TransaktionDetail> getTransaktionDetailData() {
        return transaktionDetailData;
    }
	
	/**
	 * load transaktion details from DB for selected transaktion
	 * @param tr_id
	 * @throws Exception 
	 */
	public void getTransaktionDetailFromDB(int tr_id) throws Exception {
		try {
			// first of all clear old data
			transaktionDetailData.removeAll(this.transaktionDetail);
			trdoc.TransaktionsDetailTable.setItems(getTransaktionDetailData());
			
			this.resultSet = getData("db_transaktion_detail", 
								"transaktions_detail_id, transaktions_detail_nr, transaktions_detail_created, transaktions_detail_betrag, transaktions_detail_text", 
								"where transaktions_id = " + tr_id);
			
			// perpare table
		    while (this.resultSet.next()) {

			      int trd_id = this.resultSet.getInt("transaktions_detail_id");
			      int trd_nr = this.resultSet.getInt("transaktions_detail_nr");
			      String trd_date = this.resultSet.getString("transaktions_detail_created");
			      double trd_betrag = this.resultSet.getDouble("transaktions_detail_betrag");
			      String trd_text = this.resultSet.getString("transaktions_detail_text");
			      //String tr_hash = this.resultSet.getString("transaktions_hash");
			      
			      transaktionDetailData.add(new TransaktionDetail(tr_id, trd_nr, LocalDate.parse(trd_date), trd_betrag, trd_text, 9999));
					
					trdoc.trdCreatedColumn.setCellValueFactory(cellDate -> cellDate.getValue().TransaktionsDetailCreationDateProperty());
					trdoc.trdBetragColumn.setCellValueFactory(cellDate -> cellDate.getValue().TransaktionsDetailBetragProperty());
					trdoc.trdTextColumn.setCellValueFactory(cellDate -> cellDate.getValue().TransaktionsDetailTextProperty());
					trdoc.trdNRColumn.setCellValueFactory(cellDate -> cellDate.getValue().TransaktionsDetailNrProperty());
					trdoc.trdTypeColumn.setCellValueFactory(cellDate -> cellDate.getValue().TransaktionsDetailTypeProperty());
		    }
		    
		    //load data to table
		    trdoc.TransaktionsDetailTable.setItems(getTransaktionDetailData());
		} catch (NullPointerException e) {
			System.out.println("Es konnten keine Daten gefunden werden");
		} finally {
		    close();
		}
	}

	public void decreaseDefaultDetail(int tr_id, double korrektur) {
		// update default detail to match transaktion_betrag
	}

	/**
	 * Insert new Detail in DB for transaktion
	 * @param tr_id
	 * @param trd_type
	 * @param trd_text
	 * @param trd_betrag
	 */
	public void insertTransaktionDetail(int tr_id, int trd_type, String trd_text, double trd_betrag) {
		// insert into db_transaktion_detail
	}

	/**
	 * Update already existing detail
	 * @param tr_id
	 * @param trd_type
	 * @param trd_text
	 * @param trd_betrag
	 */
	public void updateTransaktionDetail(int tr_id, int trd_type, String trd_text, double trd_betrag) {
		// update db_transaktion_detail
	}

	/**
	 * delete detail
	 * @param trd_id
	 */
	public void deletedTransaktionDetail(int trd_id) {
		// should we use a disabled status or remove from DB?
	}

	public void attachBill() {
		// attach bill to detail
		// this is an optional feature
		// dont know yet which paramters we need, trd_id + trd_bill(jpeg??)
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
	
    public void setController(TransaktionDetailOverviewController trdoc) {
        this.trdoc = trdoc;
    }

}
