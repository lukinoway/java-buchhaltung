package konto.data.DBUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;

import konto.data.model.Category;
import konto.data.model.Konto;
import konto.data.model.Transaktion;
import konto.ui.view.Transaktion.TransaktionsContainer;

public class TransaktionDBUtil extends DBCommunicator implements ITransaktion {

	private static final long serialVersionUID = 1L;
	private ResultSet resSet = null;
	private PreparedStatement pStmt = null;
	
	public TransaktionDBUtil() {
		super();
	}

	@Override
	public void createTransaktion(Transaktion transaktion) {
		try {
			String pSql = "insert into db_transaktion(transaktions_date, transaktions_betrag, transaktions_type, konto_id, transaktion_hash) values(?, ?, ?, ?, ?)";
			pStmt = connect.prepareStatement((pSql), Statement.RETURN_GENERATED_KEYS);
			pStmt.setdate(1,(Date)transaktion.getTransaktionsDate());
			pStmt.setDouble(2, transaktion.getTransaktionsBetrag());
			pStmt.setInt(3, transaktion.getTypeId());
			pStmt.setInt(4, transaktion.getKontoId());
			pStmt.setString(5, transaktion.getTransaktionsHash());
			pStmt.executeUpdate();
			
			resSet = pStmt.getGeneratedKeys();
			resSet.next();
			
			transaktion.setTransaktionsId(resSet.getInt(1));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
	}

	@Override
	public void updateTransaktion(Transaktion transaktion) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteTransaktion(Transaktion transaktion) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TransaktionsContainer selectDataByDate(LocalDate begin, LocalDate end, Konto konto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransaktionsContainer selectDataByType(Konto konto, Category category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransaktionsContainer selectDataByTimeType(LocalDate begin, LocalDate end, Konto konto, Category category) {
		// TODO Auto-generated method stub
		return null;
	}

}