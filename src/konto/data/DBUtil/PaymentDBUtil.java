package konto.data.DBUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import konto.data.Util.DateConverter;
import konto.data.container.PaymentContainer;
import konto.data.model.LoginUser;
import konto.data.model.PaymentOrder;
import konto.data.model.PaymentStatus;

public class PaymentDBUtil extends DBCommunicator implements IPayment {

    private static final long serialVersionUID = 1L;
    private ResultSet resSet = null;
    private PreparedStatement pStmt = null;

    @Override
    public void createPayment(PaymentOrder payment) {
	try {
	    String pSql = "insert into db_payment(payment_creator_konto, payment_borrower_konto, payment_text, payment_betrag, payment_datum, payment_status) " 
		    	+ " values(?, ?, ?, ?, ?, ?)";
	    pStmt = connect.prepareStatement((pSql), Statement.RETURN_GENERATED_KEYS);
	    pStmt.setInt(1, payment.getErstellerKontoId());
	    pStmt.setInt(2, payment.getSchuldnerKontoId());
	    pStmt.setString(3, payment.getPaymentText());
	    pStmt.setDouble(4, payment.getBetrag());
	    pStmt.setDate(5, DateConverter.convertLocalDateToSqlDate(payment.getDate()));
	    pStmt.setInt(6, payment.getStatus().getStatusCode());
	    pStmt.executeUpdate();

	    resSet = pStmt.getGeneratedKeys();
	    resSet.next();

	    payment.setPaymentId(resSet.getInt(1));
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    close();
	}
	
    }

    @Override
    public void updatePayment(PaymentOrder payment) {
	try {
	    String pSql = "update db_payment set "
		    	+ "payment_creator_konto = ? , payment_borrower_konto = ? , payment_text = ? , "
		    	+ "payment_betrag = ? , payment_datum = ? , payment_status = ? "
		    	+ "where payment_id = ?";
	    System.out.println(pSql);
	    pStmt = connect.prepareStatement((pSql), Statement.RETURN_GENERATED_KEYS);
	    pStmt.setInt(1, payment.getErstellerKontoId());
	    pStmt.setInt(2, payment.getSchuldnerKontoId());
	    pStmt.setString(3, payment.getPaymentText());
	    pStmt.setDouble(4, payment.getBetrag());
	    pStmt.setDate(5, DateConverter.convertLocalDateToSqlDate(payment.getDate()));
	    pStmt.setInt(6, payment.getStatus().getStatusCode());
	    pStmt.setInt(7, payment.getPaymentId());
	    pStmt.executeUpdate();

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    close();
	}
	
    }
    
    @Override
    public void updatePaymentStatus(PaymentOrder payment) {
	try {
	    String pSql = "update db_payment set payment_status = ? where payment_id = ?";
	    pStmt = connect.prepareStatement(pSql);
	    pStmt.setInt(1, payment.getStatus().getStatusCode());
	    pStmt.setInt(2, payment.getPaymentId());
	    pStmt.executeUpdate();
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    close();
	}
	
    }

    @Override
    public void deletePayment(PaymentOrder payment) {
	try {
	    String pSql = "delete from db_payment where payment_id = ?";
	    pStmt = connect.prepareStatement(pSql);
	    pStmt.setInt(1, payment.getPaymentId());
	    pStmt.executeUpdate();
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    close();
	}
	
    }

    @Override
    public PaymentContainer getAllPaymentsForUser(LoginUser user) {
	PaymentContainer container = null;
	try {
	    String pSql = "select payment_id, payment_creator_konto, payment_borrower_konto, payment_text, payment_betrag, payment_datum, payment_status"
		    	+ "  from db_payment "
		    	+ " where payment_creator_konto IN (select konto_id from db_konto where owner = ?)"
		    	+ "    or payment_borrower_konto IN (select konto_id from db_konto where owner = ?)";
	    
	    pStmt = connect.prepareStatement(pSql);
	    pStmt.setInt(1, user.getUserId());
	    pStmt.setInt(2, user.getUserId());
	    
	    resSet = pStmt.executeQuery();
	    
	    ArrayList<PaymentOrder> paymentList = new ArrayList<PaymentOrder>();
	    while(resSet.next()) {
		paymentList.add(new PaymentOrder(resSet.getInt(1), resSet.getString(4), resSet.getInt(2), 
			resSet.getInt(3), resSet.getDouble(5), DateConverter.convertDateToLocalDate(resSet.getDate(6)), PaymentStatus.fromStatus(resSet.getInt(7))));
		System.out.println("Status: " + resSet.getInt(7));
		System.out.println(PaymentStatus.fromStatus(resSet.getInt(7)).toString());
	    }
	    container = new PaymentContainer(paymentList);
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    close();
	}
	return container;
    }

    @Override
    public PaymentContainer getOpenPaymentsForUser(LoginUser user) {
	// TODO Auto-generated method stub
	return null;
    }
    
    // Close everything
    public void close() {
	try {
	    if (resSet != null) {
		resSet.close();
	    }
	    if (pStmt != null) {
		pStmt.close();
	    }
	    super.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }



}
