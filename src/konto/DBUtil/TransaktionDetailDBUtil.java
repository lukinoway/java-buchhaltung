package konto.DBUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import konto.model.Transaktion;
import konto.model.TransaktionDetail;
import konto.view.TransaktionDetailOverviewController;
import konto.view.TransaktionOverviewController;

public class TransaktionDetailDBUtil extends DBCommunicator {

	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	
	private TransaktionDetail transaktionDetail;
	
	private TransaktionDetailOverviewController trdoc;
	private ObservableList<TransaktionDetail> transaktionDetailData = FXCollections.observableArrayList();
	
	// basic constructor
	public TransaktionDetailDBUtil() {
		super();
	}
	
    public ObservableList<TransaktionDetail> getTransaktionDetailData() {
        return transaktionDetailData;
    }
	
	/**
	 * load transaktion details from DB for selected transaktion
	 * @param tr_id
	 * @throws Exception 
	 */
	public void getTransaktionDetailFromDB(int tr_id) throws Exception {
		try {
			// first of all clear old data
			transaktionDetailData.removeAll(this.transaktionDetail);
			trdoc.TransaktionsDetailTable.setItems(getTransaktionDetailData());
			
			this.resultSet = getData("db_transaktion_detail", 
								"transaktions_detail_id, transaktions_detail_nr, transaktions_detail_created, transaktions_detail_betrag, transaktions_detail_text, transaktions_detail_anhang_vorhanden", 
								"where transaktions_id = " + tr_id);
			
			// perpare table
		    while (this.resultSet.next()) {

			      int trd_id = this.resultSet.getInt("transaktions_detail_id");
			      int trd_nr = this.resultSet.getInt("transaktions_detail_nr");
			      String trd_date = this.resultSet.getString("transaktions_detail_created");
			      double trd_betrag = this.resultSet.getDouble("transaktions_detail_betrag");
			      String trd_text = this.resultSet.getString("transaktions_detail_text");
			      boolean trd_billavailable = this.resultSet.getBoolean("transaktions_detail_anhang_vorhanden");
			      //String tr_hash = this.resultSet.getString("transaktions_hash");
			      
			      transaktionDetailData.add(new TransaktionDetail(tr_id, trd_id, trd_nr, LocalDate.parse(trd_date), trd_betrag, trd_text, 9999, trd_billavailable));
					
			      trdoc.trdCreatedColumn.setCellValueFactory(cellDate -> cellDate.getValue().TransaktionsDetailCreationDateProperty());
			      trdoc.trdBetragColumn.setCellValueFactory(cellDate -> cellDate.getValue().TransaktionsDetailBetragProperty());
			      trdoc.trdTextColumn.setCellValueFactory(cellDate -> cellDate.getValue().TransaktionsDetailTextProperty());
			      trdoc.trdNRColumn.setCellValueFactory(cellDate -> cellDate.getValue().TransaktionsDetailNrProperty());
			      trdoc.trdTypeColumn.setCellValueFactory(cellDate -> cellDate.getValue().TransaktionsDetailTypeProperty());
			      trdoc.trdBillAvailable.setCellValueFactory(cellDate -> cellDate.getValue().TransaktionsDetailBillAvailable());
		    }
		    
		    //load data to table
		    trdoc.TransaktionsDetailTable.setItems(getTransaktionDetailData());
		} catch (NullPointerException e) {
			System.out.println("Es konnten keine Daten gefunden werden");
		} finally {
		    close();
		}
	}

	public void decreaseDefaultDetail(int tr_id, double korrektur) {
		// update default detail to match transaktion_betrag
	}

	/**
	 * Insert new Detail in DB for transaktion
	 * @param tr_id
	 * @param trd_nr
	 * @param trd_text
	 * @param trd_betrag
	 * @throws Exception 
	 */
	public void insertTransaktionDetail(int tr_id, String trd_text, double trd_betrag) throws Exception {
		// insert into db_transaktion_detail
		try {
			int trd_nr = 0;
			System.out.println("tr_id = " + tr_id);
			this.resultSet = getData("db_transaktion_detail", 
									 "max(transaktions_detail_nr) as anzahl", 
									 "where transaktions_id = " +  tr_id );
			// set new detail_nr
			while (this.resultSet.next()) {
				trd_nr = this.resultSet.getInt("anzahl");
			}
			// is this really needed?
			//close();
			
			// increase trd_nr by 1
			trd_nr = trd_nr + 1;
			
			// insert new data
			insertData("db_transaktion_detail", 
						tr_id + ", " + trd_nr + ", " + trd_betrag + ", \"" + trd_text + "\", curdate()",
						"transaktions_id, transaktions_detail_nr, transaktions_detail_betrag, transaktions_detail_text, transaktions_detail_created");
			
		} catch (NullPointerException e) {
			System.out.println("Es konnten keine Daten gefunden werden");
		} finally {
			close();
		}
	}

	/**
	 * Update already existing detail
	 * @param trd_id
	 * @param trd_text
	 * @param trd_betrag
	 * @throws Exception 
	 */
	public void updateTransaktionDetail(int trd_id, String trd_text, double trd_betrag) throws Exception {
		try {
			// update db_transaktion_detail
			updateData("db_transaktion_detail", "transaktions_detail_text = \"" + trd_text + "\" , transaktions_detail_betrag =" + trd_betrag ,"transaktions_detail_id =" + trd_id );
		} catch (NullPointerException e) {
			System.out.println("Es konnten keine Daten gefunden werden");
		} finally {
			close();
		}
	}

	/**
	 * delete detail
	 * @param trd_id
	 * @throws Exception 
	 */
	public void deleteTransaktionDetail(int trd_id) throws Exception {
		try{
			// should we use a disabled status or remove from DB?
			deleteData("db_transaktion_detail", "transaktions_detail_id =" +trd_id);
		} catch (NullPointerException e) {
			System.out.println("Es konnten keine Daten gefunden werden");
		} finally {
			close();
		}
	}

	/**
	 * This Function will add a bill to the selected detail
	 * @param trd_id
	 * @param tr_id
	 * @param rechnung
	 */
	public void attachBill(int trd_id, int tr_id, File rechnung) {
		try {
		    // prepared Statement execution, otherwise we are not able to load BLOB
			
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://" + server_name + "/konto_app?"+"user=" + db_user + "&password=" + db_pwd);

		    String pSql = "insert into konto_app.db_transaktion_rechnung"
		    			+ "(transaktions_detail_id, transaktions_id, transaktions_anhang, transaktions_anhang_filetype, created )"
		    			+ "values(?,?,?,?,?)" ;
		    
		    PreparedStatement pStmt = connect.prepareStatement((pSql), Statement.RETURN_GENERATED_KEYS);
		    
		    pStmt.setInt(1, trd_id);
		    pStmt.setInt(2, tr_id);
		    
		    InputStream rechnungStream = new BufferedInputStream( new FileInputStream(rechnung));
		    System.out.println("Filesize: " + rechnung.length());
		    pStmt.setBinaryStream(3, rechnungStream, rechnungStream.available());
		    
		    // store filetype in a sperate field
		    pStmt.setString(4, getFileExtension(rechnung));
		    
		    pStmt.setDate(5, Date.valueOf(LocalDate.now()));
		    pStmt.executeUpdate();
		    
		    // get transaktions_anhang_id from resultset
		    ResultSet rs = pStmt.getGeneratedKeys();
		    rs.next();
		    int tr_anhang_id = rs.getInt(1);
		    		    			
			// set transaktions_detail_anhang_vorhanden to TRUE and add transaktions_anhang_id
			updateData("db_transaktion_detail", "transaktions_anhang_id =" + tr_anhang_id + " , transaktions_detail_anhang_vorhanden =" + 1 , "transaktions_detail_id =" + trd_id );
			
		} catch (Exception e) {
			System.out.println("attachBill - Fehler trat auf");
			e.printStackTrace();
		} finally {
			close();
		}

	}
	
	/**
	 * This function will download the data from DB
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
	
	/** 
	 * This function is used to get the extension
	 * @param file
	 * @return
	 */
	public String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        return fileName.substring(fileName.lastIndexOf("."));
        else return "";
    }
	
	
	// You need to close the resultSet
	private void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (this.resultSet != null) {
				this.resultSet.close();
			}
		    if (statement != null) {
			    statement.close();
			}
		    if (connect != null) {
			    connect.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
    } 
	
    public void setController(TransaktionDetailOverviewController trdoc) {
        this.trdoc = trdoc;
    }

}
