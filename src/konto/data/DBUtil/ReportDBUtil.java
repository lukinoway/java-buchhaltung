package konto.data.DBUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import konto.data.Util.DateConverter;
import konto.data.model.LoginUser;

public class ReportDBUtil extends DBCommunicator implements IReport{

    private static final long serialVersionUID = 3543496114815882596L;
    private ResultSet resSet = null;
    private PreparedStatement pStmt = null;
    
    public ReportDBUtil() {
	super();
    }

    @Override
    public ResultSet getMonthReport(LoginUser user, LocalDate monthYear) {
	try {
	    String pSql = "SELECT t.transaktion_id, t.transaktion_date, transaktion_betrag, transaktion_text, "
		    	+ "       tt.type_text, k.konto_desc_text "
		    	+ "  FROM db_transaktion t "
		    	+ "  LEFT JOIN db_konto k on k.konto_id = t.konto_id "
		    	+ "  LEFT JOIN db_transaktion_type tt on tt.type_id = t.transaktion_type"
		    	+ " WHERE k.owner = ? AND date_trunc('month', t.transaktion_date) = date_trunc('month', cast( ? as timestamp))"
		    	+ " ORDER BY tt.type_text, t.transaktion_date;";

	    System.out.println(pSql);
	    pStmt = connect.prepareStatement(pSql);
	    pStmt.setInt(1, user.getUserId());
	    pStmt.setDate(2, DateConverter.convertLocalDateToSqlDate(monthYear));
	    resSet = pStmt.executeQuery();
	    
	    return resSet;
	} catch (Exception e) {
	    e.printStackTrace();
	    return null;
	} finally {
	    //close();
	}
    }

    @Override
    public ResultSet getYearReport(LoginUser user, LocalDate year) {
	try {
	    String pSql = "SELECT t.transaktion_id, t.transaktion_date, transaktion_betrag, transaktion_text, "
		    	+ "       tt.type_text, k.konto_desc_text "
		    	+ "  FROM db_transaktion t "
		    	+ "  LEFT JOIN db_konto k on k.konto_id = t.konto_id "
		    	+ "  LEFT JOIN db_transaktion_type tt on tt.type_id = t.transaktion_type"
		    	+ " WHERE k.owner = ? AND date_trunc('year', t.transaktion_date) = date_trunc('year', cast( ? as timestamp))"
		    	+ " ORDER BY tt.type_text, t.transaktion_date;";

	    pStmt = connect.prepareStatement(pSql);
	    pStmt.setInt(1, user.getUserId());
	    pStmt.setDate(2, DateConverter.convertLocalDateToSqlDate(year));
	    resSet = pStmt.executeQuery();
	    
	    return resSet;
	} catch (Exception e) {
	    e.printStackTrace();
	    return null;
	} finally {
	    //close();
	}
    }

}
