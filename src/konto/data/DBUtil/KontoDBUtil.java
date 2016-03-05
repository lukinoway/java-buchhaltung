package konto.data.DBUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import konto.data.model.Category;
import konto.data.model.Konto;
import konto.data.model.LoginUser;
import konto.ui.view.Category.CategoryContainer;
import konto.ui.view.Konto.KontoContainer;

public class KontoDBUtil extends DBCommunicator implements IKonto {
	
	private static final long serialVersionUID = 1L;
	private ResultSet resSet = null;
	private PreparedStatement pStmt = null;
	
	public KontoDBUtil() {
		super();
	}

	@Override
	public void createKonto(Konto konto) {
		try {
			String pSql = "insert into db_konto(konto_nr, konto_desc_text, owner) values(?, ?, ?)";
			pStmt = connect.prepareStatement((pSql), Statement.RETURN_GENERATED_KEYS);
			pStmt.setString(1, konto.getKontoNr());
			pStmt.setString(2, konto.getKontoName());
			pStmt.setInt(3, konto.getUserId());
			pStmt.executeUpdate();
			
			resSet = pStmt.getGeneratedKeys();
			resSet.next();
			
			konto.setKontoId(resSet.getInt(1));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
	}

	@Override
	public void updateKonto() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteKonto(Konto konto) {
		try {
			String pSql = "delete from db_konto where konto_id = ?";
			pStmt = connect.prepareStatement(pSql);
			pStmt.setInt(1, konto.getKontoId());
			pStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
	}

	@Override
	public KontoContainer getKontoForUser(LoginUser user) {
		KontoContainer data = null;
		try {
			String pSql = "select konto_id, konto_nr, konto_desc_text, owner from db_konto where owner = ?";
			pStmt = connect.prepareStatement(pSql);
			pStmt.setInt(1, user.getUserId());
			resSet = pStmt.executeQuery();
			
			ArrayList<Konto> kontoList = new ArrayList<Konto>();
			while(resSet.next()) {
				kontoList.add(new Konto(resSet.getInt(1), resSet.getString(2), resSet.getString(3), resSet.getInt(4)));
			}		
			data = new KontoContainer(kontoList);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return data;
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
