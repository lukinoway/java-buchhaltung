package konto.DBUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.time.*;

public class DBCommunicator {
	  private Connection connect = null;
	  private Statement statement = null;
	  private PreparedStatement preparedStatement = null;
	  public ResultSet resultSet = null;
	  
	  // connection information
	  String server_name = "192.168.1.248";
	  String db_user="konto";
	  String db_pwd="konto";
	  
	  public void getData(String tablename, String selectpart, String wherepart ) throws Exception {
		    try {
			      // This will load the MySQL driver, each DB has its own driver
			      Class.forName("com.mysql.jdbc.Driver");
			      // Setup the connection with the DB
			      connect = DriverManager.getConnection("jdbc:mysql://" + server_name + "/konto_app?"+"user=" + db_user + "&password=" + db_pwd);

			      // Statements allow to issue SQL queries to the database
			      statement = connect.createStatement();
			      
			      // Result set get the result of the SQL query
			      resultSet = statement.executeQuery("select " + selectpart + " from konto_app." + tablename + " " + wherepart);
			      //writeResultSet(resultSet);
			      
			    } catch (Exception e) {
			      throw e;
			    } finally {
			      close();
			    }
	  }
	  
	  private void writeMetaData(ResultSet resultSet) throws SQLException {
		    //   Now get some metadata from the database
		    // Result set get the result of the SQL query
		    
		    System.out.println("The columns in the table are: ");
		    
		    System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
		    for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
		      System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
		    }
		  }

		  private void writeResultSet(ResultSet resultSet) throws SQLException {
		    // ResultSet is initially before the first data set
		    while (resultSet.next()) {
		      // It is possible to get the columns via name
		      // also possible to get the columns via the column number
		      // which starts at 1
		      // e.g. resultSet.getSTring(2);
		      String hash = resultSet.getString("transaktions_hash");
		      String konto = resultSet.getString("konto_id");
		      double betrag = resultSet.getDouble("transaktions_betrag");
		      Date date = resultSet.getDate("transaktions_datum");
		      String text = resultSet.getString("transaktions_text");
		      System.out.println("HASH: " + hash);
		      System.out.println("KONTO: " + konto);
		      System.out.println("BETRAG: " + betrag);
		      System.out.println("DATUM: " + date);
		      System.out.println("TEXT: " + text);
		    }
		  }

		  // You need to close the resultSet
		  private void close() {
		    try {
		      if (resultSet != null) {
		        resultSet.close();
		      }

		      if (statement != null) {
		        statement.close();
		      }

		      if (connect != null) {
		        connect.close();
		      }
		    } catch (Exception e) {

		    }
		  } 
}
