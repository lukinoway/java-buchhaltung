package konto.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import konto.model.Konto;

import java.time.*;

public class DB_Util {
	  private Connection connect = null;
	  private Statement statement = null;
	  private PreparedStatement preparedStatement = null;
	  private ResultSet resultSet = null;

	  
	  /**
	   * with this function we will add new data to 
	   * @param kn
	   * @throws Exception
	   */
	  public void writeDataBase(Konto kn) throws Exception {
		    try {
		      // This will load the MySQL driver, each DB has its own driver
		      Class.forName("com.mysql.jdbc.Driver");
		      
		      // Setup the connection with the DB
		      connect = DriverManager.getConnection("jdbc:mysql://192.168.1.248/konto_app?"+"user=konto&password=konto");

		      // Statements allow to issue SQL queries to the database
		      statement = connect.createStatement();
		      // Result set get the result of the SQL query
		      resultSet = statement.executeQuery("select * from konto_app.db_transaktion");
		      writeResultSet(resultSet);

		      // prepare Statement and insert to DB
		      for(int i=0; i < kn.transaktion.size() ; i++) {
		      // PreparedStatements can use variables and are more efficient
		    	  if (kn.transaktion.get(i).getTransaktions_text()==""){
		    		  return;
		    	  }
		    	  preparedStatement = connect
		    		  .prepareStatement("insert into  konto_app.db_transaktion(transaktions_datum, transaktions_betrag, transaktions_text, konto_id, transaktions_hash) "
		    				          + "values (?, ?, ?, ? , ?)");
		      
			      preparedStatement.setString(1,  kn.transaktion.get(i).getTransaktions_date().toString());
			      preparedStatement.setDouble(2,  kn.transaktion.get(i).getTransaktions_betrag());
			      preparedStatement.setString(3,  kn.transaktion.get(i).getTransaktions_text());
			      preparedStatement.setInt(4, 1);
			      preparedStatement.setString(5,  kn.transaktion.get(i).getTransaktions_hash());
			      preparedStatement.executeUpdate();
		      
			      System.out.println("Insert Entry_NR: " + i + " currenttime: " + LocalTime.now() );
		      }
		  
		      
		    } catch (Exception e) {
		    	e.printStackTrace();
		    } finally {
		      close();
		    }

		  }
	  
	  // dont use this
	  /*
	  public void readDataBase() throws Exception {
		    try {
		      // This will load the MySQL driver, each DB has its own driver
		      Class.forName("com.mysql.jdbc.Driver");
		      // Setup the connection with the DB
		      connect = DriverManager.getConnection("jdbc:mysql://192.168.1.248/konto_app?"+"user=konto&password=konto");

		      // Statements allow to issue SQL queries to the database
		      statement = connect.createStatement();
		      // Result set get the result of the SQL query
		      resultSet = statement.executeQuery("select * from konto_app.db_transaktion");
		      writeResultSet(resultSet);

		      // PreparedStatements can use variables and are more efficient
		      preparedStatement = connect
		    		  .prepareStatement("insert into  konto_app.db_transaktion(transaktions_datum, transaktions_betrag, transaktions_text, konto_id, transaktions_hash) "
		    				          + "values (?, ?, ?, ? , ?)");
		      // "myuser, webpage, datum, summery, COMMENTS from feedback.comments");
		      // Parameters start with 1
		      preparedStatement.setDate(1, new java.sql.Date(2009, 12, 11));
		      preparedStatement.setDouble(2, -88.0);
		      preparedStatement.setString(3, "Test-Transaktion");
		      preparedStatement.setInt(4, 1);
		      preparedStatement.setString(5, "TestHASH");
		      preparedStatement.executeUpdate();

		      preparedStatement = connect
		          .prepareStatement("SELECT transaktions_datum, transaktions_betrag, transaktions_text, konto_id, transaktions_hash from konto_app.db_transaktion");
		      resultSet = preparedStatement.executeQuery();
		      writeResultSet(resultSet);

		      // Remove again the insert comment
		      preparedStatement = connect
		      .prepareStatement("delete from konto_app.db_transaktion where konto_id= ? ; ");
		      preparedStatement.setInt(1, 1);
		      preparedStatement.executeUpdate();
		      
		      resultSet = statement
		      .executeQuery("select * from konto_app.db_transaktion");
		      writeMetaData(resultSet);
		      
		    } catch (Exception e) {
		      throw e;
		    } finally {
		      close();
		    }

		  }
		  */

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
