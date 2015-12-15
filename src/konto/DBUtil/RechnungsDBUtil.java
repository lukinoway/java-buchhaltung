package konto.DBUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import konto.model.Rechnung;

public class RechnungsDBUtil extends DBCommunicator implements IRechnung {

    private ResultSet resSet = null;

    public RechnungsDBUtil() {
	super();
    }

    public void linkBillToTransaktion(int tr_id, int billId) throws Exception {
	// insert into db_transaktion_detail
	try {
	    int trd_nr = 0;
	    System.out.println("tr_id = " + tr_id);
	    this.resultSet = getData("db_transaktion_detail", "max(transaktions_detail_nr) as anzahl",
		    "where transaktions_id = " + tr_id);
	    // set new detail_nr
	    while (this.resultSet.next()) {
		trd_nr = this.resultSet.getInt("anzahl");
	    }

	    // increase trd_nr by 1
	    trd_nr = trd_nr + 1;

	    // Detail Text
	    String detail = "Verlinkte Rechnung";
	    // insert new data
	    ResultSet rs = insertDataPrepared("db_transaktion_detail",
		    tr_id + ", " + trd_nr + ", " + 0 + ", \"" + detail + "\", curdate()",
		    "transaktions_id, transaktions_detail_nr, transaktions_detail_betrag, transaktions_detail_text, transaktions_detail_created");
	    rs.next();
	    int trd_id = rs.getInt(1);

	    // set trd_id / tr_id in db_transaktion_rechnung
	    updateData("db_transaktion_rechnung",
		    "transaktions_detail_id = " + trd_id + " , transaktions_id = " + tr_id,
		    "transaktions_anhang_id =" + billId);
	    // set transaktions_detail_anhang_vorhanden to TRUE and add
	    // transaktions_anhang_id
	    updateData("db_transaktion_detail",
		    "transaktions_anhang_id =" + billId + " , transaktions_detail_anhang_vorhanden =" + 1,
		    "transaktions_detail_id =" + trd_id);

	    rs.close();

	} catch (NullPointerException e) {
	    System.out.println("Es konnten keine Daten gefunden werden");
	} finally {
	    close();
	}
    }

    /**
     * insert new Rechnung to DB
     */
    public void insertRechnung(Rechnung rechnung) {
	try {
	    // prepared Statement execution, otherwise we are not able to load
	    // BLOB
	    super.connectDB();

	    String pSql = "insert into konto_app.db_transaktion_rechnung"
		    + "(transaktions_anhang, transaktions_anhang_filetype, created, transaktions_anhang_text )"
		    + "values(?,?,?,?)";

	    PreparedStatement pStmt = super.connect.prepareStatement((pSql), Statement.RETURN_GENERATED_KEYS);

	    InputStream rechnungStream = new BufferedInputStream(new FileInputStream(rechnung.getRechnung()));
	    System.out.println("Filesize: " + rechnung.getRechnung().length());
	    pStmt.setBinaryStream(1, rechnungStream, rechnungStream.available());

	    // store filetype in a sperate field
	    pStmt.setString(2, getFileExtension(rechnung.getRechnung()));

	    pStmt.setDate(3, Date.valueOf(LocalDate.now()));
	    pStmt.setString(4, rechnung.getRechnungsText());
	    pStmt.executeUpdate();

	    // get transaktions_anhang_id from resultset
	    resSet = pStmt.getGeneratedKeys();
	    resSet.next();
	    System.out.println("Added bill to DB, RechnungsNR: " + resSet.getInt(1));

	    if (this.resSet != null) {
		this.resSet.close();
	    }
	} catch (Exception e) {
	    System.out.println("attachBill - Fehler trat auf");
	    e.printStackTrace();
	} finally {
	    super.close();
	}
    }

    @Override
    public void updateRechnung(Rechnung rechnung) {
	// TODO Auto-generated method stub

    }

    public void deleteRechnung(int billId) {
	try {
	    deleteData("db_transaktion_rechnung", "transaktions_anhang_id =" + billId);
	} catch (NullPointerException e) {
	    System.out.println("Es konnten keine Daten gefunden werden");
	} catch (Exception e) {
	    System.out.println("Hier lief was shief - deleteBillFromPool");
	    e.printStackTrace();
	} finally {
	    super.close();
	}
    }

    public String downloadRechnung(int billId) {
	String filepath = null;
	try {
	    this.resSet = getData("db_transaktion_rechnung",
		    "transaktions_anhang_id, transaktions_anhang_filetype, transaktions_anhang",
		    "where transaktions_anhang_id =" + billId);

	    this.resSet.next();

	    int tra_id = this.resSet.getInt(1);
	    String fileext = this.resSet.getString(2);
	    Blob blob = this.resSet.getBlob(3);

	    String filename = "Rechnung_" + tra_id + "_";

	    // write some log output
	    System.out.println("Read " + blob.length() + " bytes");

	    byte[] buff = blob.getBytes(1, (int) blob.length());
	    File output = File.createTempFile(filename, fileext, new File("C:\\Windows\\Temp"));
	    FileOutputStream out = new FileOutputStream(output);
	    out.write(buff);
	    out.close();

	    System.out.println("created File:" + output.getAbsolutePath());
	    filepath = output.getAbsolutePath();

	    if (this.resSet != null) {
		this.resSet.close();
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    close();
	}
	return filepath;
    }

    public ArrayList<Rechnung> selectDataFromPool() {
	ArrayList<Rechnung> rechnungsCollector = new ArrayList<Rechnung>();
	try {
	    this.resSet = getData("db_transaktion_rechnung",
		    "transaktions_anhang_id, created, transaktions_anhang_text",
		    "where transaktions_detail_id IS null and transaktions_id IS null");

	    // fill table
	    while (this.resSet.next()) {
		int billId = this.resSet.getInt(1);
		String billDate = this.resSet.getString(2);
		String billText = this.resSet.getString(3);

		System.out.print("ID:" + billId + " / Date:" + billDate + " / Text:" + billText);
		rechnungsCollector.add(new Rechnung(billId, LocalDate.parse(billDate), billText));
	    }

	    if (this.resSet != null) {
		this.resSet.close();
	    }
	} catch (NullPointerException e) {
	    System.out.println("keine Daten im Pool vorhanden!");
	} catch (Exception e) {
	    System.out.println("Hier lief was schief - loadRechnungsPool");
	    e.printStackTrace();
	} finally {
	    super.close();
	}
	return rechnungsCollector;
    }

    /**
     * This function is used to get the extension
     * 
     * @param file
     * @return
     */
    public String getFileExtension(File file) {
	String fileName = file.getName();
	if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
	    return fileName.substring(fileName.lastIndexOf("."));
	else
	    return "";
    }
}
