package konto.data.DBUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

import konto.data.model.Transaktion;
import konto.data.model.TransaktionDetail;

public class TransaktionDetailDBUtil extends DBCommunicator implements ITransaktionDetail {

    private ResultSet resSet = null;

    private TransaktionDetail transaktionDetail;

    // basic constructor
    public TransaktionDetailDBUtil() {
	super();
    }

    /**
     * Update already existing detail
     * 
     * @param trd_id
     * @param trd_text
     * @param trd_betrag
     * @throws Exception
     */
    public void updateTransaktionDetail(int trd_id, String trd_text, double trd_betrag) throws Exception {

    }

    //
    // is this still usefull????
    //
    /*
     * public void attachBill(int trd_id, int tr_id, File rechnung) { try { //
     * prepared Statement execution, otherwise we are not able to load BLOB
     * 
     * Class.forName("com.mysql.jdbc.Driver"); connect =
     * DriverManager.getConnection("jdbc:mysql://" + server_name +
     * "/konto_app?"+"user=" + db_user + "&password=" + db_pwd);
     * 
     * String pSql = "insert into konto_app.db_transaktion_rechnung" +
     * "(transaktions_detail_id, transaktions_id, transaktions_anhang, transaktions_anhang_filetype, created )"
     * + "values(?,?,?,?,?)" ;
     * 
     * PreparedStatement pStmt = connect.prepareStatement((pSql),
     * Statement.RETURN_GENERATED_KEYS);
     * 
     * pStmt.setInt(1, trd_id); pStmt.setInt(2, tr_id);
     * 
     * InputStream rechnungStream = new BufferedInputStream( new
     * FileInputStream(rechnung)); System.out.println("Filesize: " +
     * rechnung.length()); pStmt.setBinaryStream(3, rechnungStream,
     * rechnungStream.available());
     * 
     * // store filetype in a sperate field pStmt.setString(4,
     * getFileExtension(rechnung));
     * 
     * pStmt.setDate(5, Date.valueOf(LocalDate.now())); pStmt.executeUpdate();
     * 
     * // get transaktions_anhang_id from resultset ResultSet rs =
     * pStmt.getGeneratedKeys(); rs.next(); int tr_anhang_id = rs.getInt(1);
     * 
     * // set transaktions_detail_anhang_vorhanden to TRUE and add
     * transaktions_anhang_id updateData("db_transaktion_detail",
     * "transaktions_anhang_id =" + tr_anhang_id +
     * " , transaktions_detail_anhang_vorhanden =" + 1 ,
     * "transaktions_detail_id =" + trd_id );
     * 
     * } catch (Exception e) { System.out.println("attachBill - Fehler trat auf"
     * ); e.printStackTrace(); } finally { close(); }
     * 
     * }
     */

    /**
     * This function will download the data from DB
     * 
     * @param trd_id
     * @return
     */
    public String downloadBill(int trd_id) {
	String filepath = null;
	try {
	    this.resultSet = getData("db_transaktion_rechnung",
		    "transaktions_anhang_id, transaktions_anhang_filetype, transaktions_anhang",
		    "where transaktions_detail_id =" + trd_id);

	    this.resultSet.next();

	    int tra_id = this.resultSet.getInt(1);
	    String fileext = this.resultSet.getString(2);
	    Blob blob = this.resultSet.getBlob(3);

	    String filename = "Rechnung_" + tra_id + "_" + trd_id;

	    // write some log output
	    System.out.println("Read " + blob.length() + " bytes");

	    byte[] buff = blob.getBytes(1, (int) blob.length());
	    File output = File.createTempFile(filename, fileext, new File("C:\\Windows\\Temp"));
	    FileOutputStream out = new FileOutputStream(output);
	    out.write(buff);
	    out.close();

	    System.out.println("created File:" + output.getAbsolutePath());
	    filepath = output.getAbsolutePath();

	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return filepath;
    }

    public void insertDetail(TransaktionDetail detail) {
	// insert into db_transaktion_detail
	try {
	    int trd_nr = 0;
	    System.out.println("tr_id = " + detail.getTransaktionsId());
	    this.resSet = getData("db_transaktion_detail", "max(transaktions_detail_nr) as anzahl",
		    "where transaktions_id = " + detail.getTransaktionsId());
	    // set new detail_nr
	    while (this.resSet.next()) {
		trd_nr = this.resSet.getInt("anzahl");
	    }

	    trd_nr = trd_nr + 1;

	    // insert new data
	    insertData("db_transaktion_detail",
		    detail.getTransaktionsId() + ", " + trd_nr + ", " + detail.getDetailBetrag() + ", \""
			    + detail.getDetailText() + "\", curdate()",
		    "transaktions_id, transaktions_detail_nr, transaktions_detail_betrag, transaktions_detail_text, transaktions_detail_created");

	} catch (NullPointerException e) {
	    System.out.println("Es konnten keine Daten gefunden werden");
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    super.close();
	}

    }

    public void updateDetail(TransaktionDetail detail) {
	try {
	    // update db_transaktion_detail
	    updateData(
		    "db_transaktion_detail", "transaktions_detail_text = \"" + detail.getDetailText()
			    + "\" , transaktions_detail_betrag =" + detail.getDetailBetrag(),
		    "transaktions_detail_id =" + detail.getDetailId());
	} catch (NullPointerException e) {
	    System.out.println("Es konnten keine Daten gefunden werden");
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    super.close();
	}

    }

    public void deleteDetail(int detailId) {
	try {
	    deleteData("db_transaktion_detail", "transaktions_detail_id =" + detailId);
	} catch (NullPointerException e) {
	    System.out.println("Es konnten keine Daten gefunden werden");
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    super.close();
	}
    }

    public ArrayList<TransaktionDetail> selectDetail(Transaktion transaktion) {
	ArrayList<TransaktionDetail> detailCollector = new ArrayList<TransaktionDetail>();
	try {
	    this.resSet = getData("db_transaktion_detail",
		    "transaktions_detail_id, transaktions_detail_nr, transaktions_detail_created, "
			    + "transaktions_detail_betrag, transaktions_detail_text, transaktions_detail_anhang_vorhanden",
		    "where transaktions_id = " + transaktion.getTransaktionsId());

	    while (this.resSet.next()) {

		int trd_id = this.resSet.getInt("transaktions_detail_id");
		int trd_nr = this.resSet.getInt("transaktions_detail_nr");
		String trd_date = this.resSet.getString("transaktions_detail_created");
		double trd_betrag = this.resSet.getDouble("transaktions_detail_betrag");
		String trd_text = this.resSet.getString("transaktions_detail_text");
		boolean trd_billavailable = this.resSet.getBoolean("transaktions_detail_anhang_vorhanden");

		detailCollector.add(new TransaktionDetail(transaktion.getTransaktionsId(), trd_id, trd_nr,
			LocalDate.parse(trd_date), trd_betrag, trd_text, 9999, trd_billavailable));
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
	return detailCollector;
    }

    /**
     * This function is used to get the extension
     * 
     * @param file
     * @return
     */
    private String getFileExtension(File file) {
	String fileName = file.getName();
	if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
	    return fileName.substring(fileName.lastIndexOf("."));
	else
	    return "";
    }
    
    // Close everything
    public void close() {
	super.close();
	super.closeConnection();
    }
}
