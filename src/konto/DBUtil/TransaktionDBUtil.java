package konto.DBUtil;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

import konto.model.Transaktion;

public class TransaktionDBUtil extends DBCommunicator implements ITransaktion {

    ResultSet resSet = null;

    /**
     * Insert new transaktion in DB
     * 
     * @param transaktion
     */
    public TransaktionDBUtil() {
	super();
    }

    /**
     * method to insert new transaktion
     */
    public void insertTransaktion(Transaktion transaktion) {
	try {
	    insertData("db_transaktion",
		    transaktion.getTransaktionsDate() + ", " + transaktion.getTransaktionsBetrag() + ", "
			    + transaktion.getTransaktionsText() + ", " + transaktion.getTransaktionsId() + ", "
			    + transaktion.getTransaktionsHash(),
		    "transaktions_datum, transaktions_betrag, transaktions_text, konto_id, transaktions_hash");
	} catch (Exception e) {
	    System.out.println("Hier lief was schief - insertTransaktion");
	    e.printStackTrace();
	} finally {
	    super.close();
	}
    }

    public void updateTransaktion(Transaktion transaktion) {
	// TODO Auto-generated method stub

    }

    /**
     * method to delete transaktion from DB
     */

    public void deleteTransaktion(Transaktion transaktion) {
	try {
	    deleteData("db_transaktion", "where transaktions_id =" + transaktion.getTransaktionsId());
	} catch (Exception e) {
	    System.out.println("hier lief was schief - deleteTransaktion");
	    e.printStackTrace();
	} finally {
	    super.close();
	}
    }

    /**
     * Method to get Data by Date
     */
    public ArrayList<Transaktion> selectDataByDate(LocalDate begin, LocalDate end, int kontoId) {
	// Build new array
	ArrayList<Transaktion> transaktionsCollector = new ArrayList<Transaktion>();

	System.out.println("Start Date: " + begin.toString());
	System.out.println("End Date:   " + end.toString());
	System.out.println("Konto ID:	" + kontoId);
	try {
	    this.resSet = getData("db_transaktion",
		    "transaktions_id, transaktions_datum, transaktions_betrag, transaktions_text, transaktions_hash",
		    "where konto_id = " + kontoId + " and transaktions_datum between \"" + begin.toString()
			    + "\" and \"" + end.toString() + "\"");

	    // read resultSet
	    while (this.resSet.next()) {

		int tr_id = this.resSet.getInt("transaktions_id");
		String tr_date = this.resSet.getString("transaktions_datum");
		double betrag = this.resSet.getDouble("transaktions_betrag");
		String tr_text = this.resSet.getString("transaktions_text");
		String tr_hash = this.resSet.getString("transaktions_hash");

		transaktionsCollector.add(new Transaktion(tr_id, LocalDate.parse(tr_date), betrag, tr_text, tr_hash));
	    }

	    if (this.resSet != null) {
		this.resSet.close();
	    }
	} catch (NullPointerException e) {
	    System.out.println("Es konnten keine Daten gefunden werden");
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    super.close();
	}
	return transaktionsCollector;
    }

    /**
     * Method to get Data by Type
     */
    public ArrayList<Transaktion> selectDataByType(int kontoId, int transaktionsType) {
	// TODO Auto-generated method stub
	return null;
    }

    public ArrayList<Transaktion> selectDataByTimeType(LocalDate begin, LocalDate end, int kontoId,
	    int transaktionsType) {
	// TODO Auto-generated method stub
	return null;
    }
    
    // Close everything
    public void close() {
	super.close();
	super.closeConnection();
    }
}
