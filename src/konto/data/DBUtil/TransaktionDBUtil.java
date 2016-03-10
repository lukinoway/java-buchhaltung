package konto.data.DBUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import konto.data.container.TransaktionsContainer;
import konto.data.model.LoginUser;
import konto.data.model.Transaktion;

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
	    String pSql = "insert into db_transaktion "
		    + "(transaktion_date, transaktion_betrag, transaktion_text, transaktion_type, konto_id, transaktion_hash) "
		    + "values(?, ?, ?, ?, ?, ?)";
	    pStmt = connect.prepareStatement((pSql), Statement.RETURN_GENERATED_KEYS);
	    pStmt.setDate(1, convertLocalDateToSqlDate(transaktion.getTransaktionsDate()));
	    pStmt.setDouble(2, transaktion.getTransaktionsBetrag());
	    pStmt.setString(3, transaktion.getTransaktionsText());
	    pStmt.setInt(4, transaktion.getTypeId());
	    pStmt.setInt(5, transaktion.getKontoId());
	    pStmt.setString(6, transaktion.getTransaktionsHash());
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
	try {
	    String pSql = "delete from db_transaktion where transaktion_id = ?";
	    pStmt = connect.prepareStatement(pSql);
	    pStmt.setInt(1, transaktion.getTransaktionsId());
	    pStmt.executeUpdate();
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    close();
	}

    }

    @Override
    public TransaktionsContainer getAllTransaktionsForUser(LoginUser user) {
	TransaktionsContainer data = null;
	try {
	    String pSql = "SELECT transaktion_id, transaktion_date, transaktion_betrag, transaktion_text, "
		    + "transaktion_type, t.konto_id, transaktion_hash " + "FROM db_transaktion t "
		    + "JOIN db_konto k on k.konto_id = t.konto_id " + "WHERE k.owner = ?";
	    pStmt = connect.prepareStatement(pSql);
	    pStmt.setInt(1, user.getUserId());
	    resSet = pStmt.executeQuery();

	    ArrayList<Transaktion> transaktionList = new ArrayList<Transaktion>();
	    System.out.println("user ID: " + user.getUserId());
	    while (resSet.next()) {

		transaktionList.add(new Transaktion(resSet.getInt(1), convertDateToLocalDate(resSet.getDate(2)),
			resSet.getDouble(3), resSet.getString(4), resSet.getString(7), resSet.getInt(6),
			resSet.getInt(5)));
	    }
	    data = new TransaktionsContainer(transaktionList);

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    close();
	}

	return data;
    }

    @Override
    public TransaktionsContainer getTransaktionsForKontoCategory(int kontoId, int categoryId) {
	TransaktionsContainer data = null;
	try {
	    String pSql = "SELECT transaktion_id, transaktion_date, transaktion_betrag, transaktion_text, "
		    + "transaktion_type, konto_id, transaktion_hash " + "FROM db_transaktion WHERE ";

	    String kontopart = "konto_id = ?";
	    String categorypart = "transaktion_type = ?";

	    // check input
	    if (kontoId == 0) {
		pSql += categorypart;
		pStmt = connect.prepareStatement(pSql);
		pStmt.setInt(1, categoryId);
	    }
	    if (categoryId == 0) {
		pSql += kontopart;
		pStmt = connect.prepareStatement(pSql);
		pStmt.setInt(1, kontoId);
	    } else {
		pSql += kontopart + " AND " + categorypart;
		pStmt = connect.prepareStatement(pSql);
		pStmt.setInt(1, kontoId);
		pStmt.setInt(2, categoryId);
	    }

	    resSet = pStmt.executeQuery();

	    ArrayList<Transaktion> transaktionList = new ArrayList<Transaktion>();
	    while (resSet.next()) {

		transaktionList.add(new Transaktion(resSet.getInt(1), convertDateToLocalDate(resSet.getDate(2)),
			resSet.getDouble(3), resSet.getString(4), resSet.getString(7), resSet.getInt(6),
			resSet.getInt(5)));
	    }
	    data = new TransaktionsContainer(transaktionList);

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    close();
	}
	return data;
    }

    @Override
    public TransaktionsContainer getTransaktionsForDateKontoCategory(LocalDate begin, LocalDate end, int kontoId,
	    int categoryId) {
	TransaktionsContainer data = null;
	try {
	    String pSql = "SELECT transaktion_id, transaktion_date, transaktion_betrag, transaktion_text, "
		    + "transaktion_type, konto_id, transaktion_hash " + "FROM db_transaktion WHERE transaktion_date between ? and ? ";

	    String kontopart = "konto_id = ?";
	    String categorypart = "transaktion_type = ?";

	    // check input
	    if (kontoId == 0) {
		pSql += " AND " + categorypart;
		pStmt = connect.prepareStatement(pSql);
		pStmt.setDate(1, convertLocalDateToSqlDate(begin));
		pStmt.setDate(2, convertLocalDateToSqlDate(end));
		pStmt.setInt(3, categoryId);
	    }
	    if (categoryId == 0) {
		pSql += " AND " + kontopart;
		pStmt = connect.prepareStatement(pSql);
		pStmt.setDate(1, convertLocalDateToSqlDate(begin));
		pStmt.setDate(2, convertLocalDateToSqlDate(end));
		pStmt.setInt(3, kontoId);
	    } else {
		pSql += " AND " + kontopart + " AND " + categorypart;
		pStmt = connect.prepareStatement(pSql);
		pStmt.setDate(1, convertLocalDateToSqlDate(begin));
		pStmt.setDate(2, convertLocalDateToSqlDate(end));
		pStmt.setInt(3, kontoId);
		pStmt.setInt(4, categoryId);
	    }

	    resSet = pStmt.executeQuery();

	    ArrayList<Transaktion> transaktionList = new ArrayList<Transaktion>();
	    while (resSet.next()) {

		transaktionList.add(new Transaktion(resSet.getInt(1), convertDateToLocalDate(resSet.getDate(2)),
			resSet.getDouble(3), resSet.getString(4), resSet.getString(7), resSet.getInt(6),
			resSet.getInt(5)));
	    }
	    data = new TransaktionsContainer(transaktionList);

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    close();
	}
	return data;
    }

    @Override
    public TransaktionsContainer getTransaktionsForMonthKontoCategory(LocalDate monthYear, int kontoId,
	    int categoryId) {
	TransaktionsContainer data = null;
	try {
	    String pSql = "SELECT transaktion_id, transaktion_date, transaktion_betrag, transaktion_text, "
		    + "transaktion_type, konto_id, transaktion_hash " + "FROM db_transaktion WHERE date_trunc('month', transaktion_date) = date_trunc('month', cast( ? as timestamp)) ";

	    String kontopart = "konto_id = ?";
	    String categorypart = "transaktion_type = ?";

	    // check input
	    if (kontoId == 0) {
		pSql += " AND " + categorypart;
		pStmt = connect.prepareStatement(pSql);
		pStmt.setDate(1, convertLocalDateToSqlDate(monthYear));
		pStmt.setInt(2, categoryId);
	    }
	    if (categoryId == 0) {
		pSql += " AND " + kontopart;
		pStmt = connect.prepareStatement(pSql);
		pStmt.setDate(1, convertLocalDateToSqlDate(monthYear));
		pStmt.setInt(2, kontoId);
	    } else {
		pSql += " AND " + kontopart + " AND " + categorypart;
		pStmt = connect.prepareStatement(pSql);
		pStmt.setDate(1, convertLocalDateToSqlDate(monthYear));
		pStmt.setInt(2, kontoId);
		pStmt.setInt(3, categoryId);
	    }
	    System.out.println("query: " + pSql);
	    resSet = pStmt.executeQuery();

	    ArrayList<Transaktion> transaktionList = new ArrayList<Transaktion>();
	    while (resSet.next()) {

		transaktionList.add(new Transaktion(resSet.getInt(1), convertDateToLocalDate(resSet.getDate(2)),
			resSet.getDouble(3), resSet.getString(4), resSet.getString(7), resSet.getInt(6),
			resSet.getInt(5)));
	    }
	    data = new TransaktionsContainer(transaktionList);

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    close();
	}
	return data;
    }

    @Override
    public TransaktionsContainer getTransaktionsForYearKontoCategory(LocalDate year, int kontoId, int categoryId) {
	TransaktionsContainer data = null;
	try {
	    String pSql = "SELECT transaktion_id, transaktion_date, transaktion_betrag, transaktion_text, "
		    + "transaktion_type, konto_id, transaktion_hash " + "FROM db_transaktion WHERE date_trunc('year', transaktion_date) = date_trunc('year', cast( ? as timestamp)) ";

	    String kontopart = "konto_id = ?";
	    String categorypart = "transaktion_type = ?";

	    // check input
	    if (kontoId == 0) {
		pSql += " AND " + categorypart;
		pStmt = connect.prepareStatement(pSql);
		pStmt.setDate(1, convertLocalDateToSqlDate(year));
		pStmt.setInt(2, categoryId);
	    }
	    if (categoryId == 0) {
		pSql += " AND " + kontopart;
		pStmt = connect.prepareStatement(pSql);
		pStmt.setDate(1, convertLocalDateToSqlDate(year));
		pStmt.setInt(2, kontoId);
	    } else {
		pSql += " AND " + kontopart + " AND " + categorypart;
		pStmt = connect.prepareStatement(pSql);
		pStmt.setDate(1, convertLocalDateToSqlDate(year));
		pStmt.setInt(2, kontoId);
		pStmt.setInt(3, categoryId);
	    }

	    resSet = pStmt.executeQuery();

	    ArrayList<Transaktion> transaktionList = new ArrayList<Transaktion>();
	    while (resSet.next()) {

		transaktionList.add(new Transaktion(resSet.getInt(1), convertDateToLocalDate(resSet.getDate(2)),
			resSet.getDouble(3), resSet.getString(4), resSet.getString(7), resSet.getInt(6),
			resSet.getInt(5)));
	    }
	    data = new TransaktionsContainer(transaktionList);

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

    private java.sql.Date convertLocalDateToSqlDate(LocalDate localdate) {
	LocalDate ld = localdate;
	Instant instant = ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
	Date res = Date.from(instant);
	return new java.sql.Date(res.getTime());
    }

    private LocalDate convertDateToLocalDate(Date date) {
	// date conversion
	Instant instant = Instant.ofEpochMilli(date.getTime());
	return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
    }

}