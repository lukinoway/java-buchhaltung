package konto.DBUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import konto.model.Rechnung;
import konto.model.Transaktion;
import konto.view.TransaktionOverviewController;

public class RechnungsDBUtil extends DBCommunicator{
	
	// main stuff
	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	
	private Rechnung rechnung;
	private TransaktionOverviewController troc;
	private ObservableList<Rechnung> rechnungsData = FXCollections.observableArrayList();
	
	public RechnungsDBUtil() {
		super();
	}
	
	/**
	 * Load unknown Bills to view
	 */
	public void loadRechnungsPool() {
		try {
			// first of all clear data if there is any
			rechnungsData.removeAll(this.rechnung);
			troc.RechnungsPoolTable.setItems(getRechnungsData());
		
			this.resultSet = getData("db_transaktion_rechnung",
									 "transaktions_anhang_id, created, transaktions_anhang_text",
									 "where transaktions_detail_id IS null and transaktions_id IS null");
			
			// fill table
			while(this.resultSet.next()) {
				int billId = this.resultSet.getInt(1);
				String billDate = this.resultSet.getString(2);
				String billText = this.resultSet.getString(3);
				
				System.out.print("ID:" + billId + " / Date:" + billDate + " / Text:" + billText);
				rechnungsData.add(new Rechnung(billId, LocalDate.parse(billDate), billText));
				
				troc.rechnungsId.setCellValueFactory(cellDate -> cellDate.getValue().RechnungsIdProperty());
				troc.rechnungsDatum.setCellValueFactory(cellDate -> cellDate.getValue().RechnungsDatumProperty());
				troc.rechnungsText.setCellValueFactory(cellDate -> cellDate.getValue().RechnungsTextProperty());
			}
			troc.RechnungsPoolTable.setItems(getRechnungsData());
			
		} catch (NullPointerException e) {
			System.out.println("keine Daten im Pool vorhanden!");
		} catch (Exception e) {
			System.out.println("Hier lief was schief - loadRechnungsPool");
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public void attachBilltoPool(File rechnung, String billText) {
		try {
		    // prepared Statement execution, otherwise we are not able to load BLOB
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://" + server_name + "/konto_app?"+"user=" + db_user + "&password=" + db_pwd);

		    String pSql = "insert into konto_app.db_transaktion_rechnung"
		    			+ "(transaktions_anhang, transaktions_anhang_filetype, created, transaktions_anhang_text )"
		    			+ "values(?,?,?,?)" ;
		    
		    PreparedStatement pStmt = connect.prepareStatement((pSql), Statement.RETURN_GENERATED_KEYS);
		    
		    InputStream rechnungStream = new BufferedInputStream( new FileInputStream(rechnung));
		    System.out.println("Filesize: " + rechnung.length());
		    pStmt.setBinaryStream(1, rechnungStream, rechnungStream.available());
		    
		    // store filetype in a sperate field
		    pStmt.setString(2, getFileExtension(rechnung));
		    
		    pStmt.setDate(3, Date.valueOf(LocalDate.now()));
		    pStmt.setString(4, billText);
		    pStmt.executeUpdate();
		    
		    // get transaktions_anhang_id from resultset
		    ResultSet rs = pStmt.getGeneratedKeys();
		    rs.next();
		    System.out.println("Added bill to DB, RechnungsNR: " + rs.getInt(1));

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
	public String downloadBillFromPool(int billId) {
		String filepath = null;
		try {
			this.resultSet = getData("db_transaktion_rechnung", 
									 "transaktions_anhang_id, transaktions_anhang_filetype, transaktions_anhang", 
									 "where transaktions_anhang_id =" + billId);

			this.resultSet.next();
			
			int tra_id = this.resultSet.getInt(1);
			String fileext = this.resultSet.getString(2);
			Blob blob = this.resultSet.getBlob(3);
			
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
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return filepath;
	}
	
	/**
	 * Delete Entry from Pool
	 * @param billId
	 */
	public void deleteBillFromPool (int billId) {
		try {
			deleteData("db_transaktion_rechnung", "transaktions_anhang_id =" + billId);
		} catch (NullPointerException e) {
			System.out.println("Es konnten keine Daten gefunden werden");
		} catch (Exception e) {
			System.out.println("Hier lief was shief - deleteBillFromPool");
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public void linkBillToTransaktion(int tr_id, int billId) throws Exception {
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
			updateData("db_transaktion_rechnung", "transaktions_detail_id = " + trd_id + " , transaktions_id = " + tr_id, "transaktions_anhang_id =" + billId);
			// set transaktions_detail_anhang_vorhanden to TRUE and add transaktions_anhang_id
			updateData("db_transaktion_detail", "transaktions_anhang_id =" + billId + " , transaktions_detail_anhang_vorhanden =" + 1 , "transaktions_detail_id =" + trd_id );
			
			rs.close();
					
		} catch (NullPointerException e) {
			System.out.println("Es konnten keine Daten gefunden werden");
		} finally {
			close();
		}
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
	
    public ObservableList<Rechnung> getRechnungsData() {
        return rechnungsData;
    }
	
    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setController(TransaktionOverviewController troc) {
        this.troc = troc;
    }
}
