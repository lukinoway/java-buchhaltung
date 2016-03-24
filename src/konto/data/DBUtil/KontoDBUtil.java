package konto.data.DBUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import konto.data.container.KontoContainer;
import konto.data.model.Konto;
import konto.data.model.LoginUser;

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
	    String pSql = "insert into db_konto(konto_nr, konto_desc_text, owner, visible, konto_transfer_info) values(?, ?, ?, ?, ?)";
	    pStmt = connect.prepareStatement((pSql), Statement.RETURN_GENERATED_KEYS);
	    pStmt.setString(1, konto.getKontoNr());
	    pStmt.setString(2, konto.getKontoName());
	    pStmt.setInt(3, konto.getUserId());
	    pStmt.setBoolean(4, konto.isVisible());
	    pStmt.setString(5, konto.getKontoTransferInfo());
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
    public void updateKonto(Konto konto) {
	try {
	    String pSql = "update db_konto set konto_nr = ?, konto_desc_text = ?, visible = ?, konto_transfer_info = ?  where konto_id = ?";
	    pStmt = connect.prepareStatement(pSql);
	    pStmt.setString(1, konto.getKontoNr());
	    pStmt.setString(2, konto.getKontoName());
	    pStmt.setBoolean(3, konto.isVisible());
	    pStmt.setString(4, konto.getKontoTransferInfo());
	    pStmt.setInt(5, konto.getKontoId());
	    pStmt.executeUpdate();
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    close();
	}

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
	    String pSql = "select konto_id, konto_nr, konto_desc_text, owner, visible from db_konto where owner = ?";
	    pStmt = connect.prepareStatement(pSql);
	    pStmt.setInt(1, user.getUserId());
	    resSet = pStmt.executeQuery();

	    ArrayList<Konto> kontoList = new ArrayList<Konto>();
	    while (resSet.next()) {
		kontoList.add(new Konto(resSet.getInt(1), resSet.getString(2), resSet.getString(3), resSet.getInt(4), resSet.getBoolean(5)));
	    }
	    data = new KontoContainer(kontoList);

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    close();
	}

	return data;
    }
    
    @Override
    public KontoContainer getVisibleKontosForUser(LoginUser user) {
	KontoContainer data = null;
	try {
	    String pSql = "select konto_id, konto_nr, konto_desc_text, owner, visible from db_konto where owner = ? and visible = true";
	    pStmt = connect.prepareStatement(pSql);
	    pStmt.setInt(1, user.getUserId());
	    resSet = pStmt.executeQuery();

	    ArrayList<Konto> kontoList = new ArrayList<Konto>();
	    while (resSet.next()) {
		kontoList.add(new Konto(resSet.getInt(1), resSet.getString(2), resSet.getString(3), resSet.getInt(4), resSet.getBoolean(5)));
	    }
	    data = new KontoContainer(kontoList);

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    close();
	}

	return data;
    }
    
    @Override
    public KontoContainer getVisibleKontos(LoginUser user) {
	KontoContainer data = null;
	try {
	    String pSql = "select konto_id, konto_nr, user_name || ' - ' || konto_desc_text, owner, visible "
		    	+ "  from db_user "
		    	+ "  join db_konto on owner = user_id "
		    	+ " where visible = true and user_id <> ?";
	    pStmt = connect.prepareStatement(pSql);
	    pStmt.setInt(1, user.getUserId());
	    resSet = pStmt.executeQuery();

	    ArrayList<Konto> kontoList = new ArrayList<Konto>();
	    while (resSet.next()) {
		kontoList.add(new Konto(resSet.getInt(1), resSet.getString(2), resSet.getString(3), resSet.getInt(4), resSet.getBoolean(5)));
	    }
	    data = new KontoContainer(kontoList);

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    close();
	}

	return data;
    }
    
    
    @Override
    public int getUserIdforKonto(int kontoId) {
	int userId = 0;
	try {
	    String pSql = "select owner from db_konto where konto_id = ?";
	    pStmt = connect.prepareStatement(pSql);
	    pStmt.setInt(1, kontoId);
	    resSet = pStmt.executeQuery();
	    
	    resSet.next();
	    userId = resSet.getInt(1);
	    
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    close();
	}
	return userId;
    }
    
    @Override
    public String getTransferInformationforKonto(int kontoId) {
	String transferInfo = null;
	try {
	    String pSql = "select konto_transfer_info from db_konto where konto_id = ?";
	    pStmt = connect.prepareStatement(pSql);
	    pStmt.setInt(1, kontoId);
	    resSet = pStmt.executeQuery();
	    
	    resSet.next();
	    transferInfo = resSet.getString(1);
	    
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    close();
	}
	return transferInfo;
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
