package konto.DBUtil;

public class TransaktionDetailDBUtil {

	/**
	 * load transaktion details from DB for selected transaktion
	 * @param tr_id
	 */
	public void getTransaktionDetailFromDB(int tr_id) {

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

}
