package konto.DBUtil;

import java.time.LocalDate;

import konto.model.Transaktion;

public class TransaktionDBUtil {

	/**
	 * Insert new transaktion in DB
	 * @param transaktion
	 */
	public void insertTransaktion(Transaktion transaktion) {
		// this will be used for csv parse and insert
	}

	/**
	 * with this Function we get Transaktion that are between start and end Date
	 * @param startDate
	 * @param endDate
	 */
	public void getTransaktionFromDB(LocalDate startDate, LocalDate endDate) {
		// get transaktions from DB
		// select * from db_transaktion
	}

}
