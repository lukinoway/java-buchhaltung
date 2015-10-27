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
	  
	  public ResultSet getData(String tablename, String selectpart, String wherepart ) throws Exception {
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
			      return resultSet;
			    } catch (Exception e) {
			      throw e;
			    } finally {
			      //close();
			    }
	  }
	  
	  /**
	   * Function to insert new Data to DB
	   * @param tablename
	   * @param valuepart
	   * @param columnpart
	   * @throws Exception
	   */
	  public void insertData(String tablename, String valuepart, String columnpart ) throws Exception {
		    try {
			      // This will load the MySQL driver, each DB has its own driver
			      Class.forName("com.mysql.jdbc.Driver");
			      // Setup the connection with the DB
			      connect = DriverManager.getConnection("jdbc:mysql://" + server_name + "/konto_app?"+"user=" + db_user + "&password=" + db_pwd);

			      // Statements allow to issue SQL queries to the database
			      statement = connect.createStatement();
			      
			      // print query
			      System.out.println("insert into konto_app." + tablename + "( " + columnpart + " ) values( " + valuepart + " )");
			      statement.executeUpdate("insert into konto_app." + tablename + "( " + columnpart + " ) values( " + valuepart + " )");

			    } catch (Exception e) {
			      throw e;
			    } finally {
			      //close();
			    }
	  }
	  
	  
	  /**
	   * Function to update data in DB
	   * @param tablename
	   * @param setpart
	   * @param wherepart
	   * @throws Exception
	   */
	  public void updateData(String tablename, String setpart, String wherepart ) throws Exception {
		    try {
			      // This will load the MySQL driver, each DB has its own driver
			      Class.forName("com.mysql.jdbc.Driver");
			      // Setup the connection with the DB
			      connect = DriverManager.getConnection("jdbc:mysql://" + server_name + "/konto_app?"+"user=" + db_user + "&password=" + db_pwd);

			      // Statements allow to issue SQL queries to the database
			      statement = connect.createStatement();
			      
			      // print query
			      System.out.println("update konto_app." + tablename + " set " + setpart + " where " + wherepart);
			      statement.executeUpdate("update konto_app." + tablename + " set " + setpart + " where " + wherepart);

			    } catch (Exception e) {
			      throw e;
			    } finally {
			      //close();
			    }
	  }
	  
	  /**
	   * Function to delete data from DB
	   * @param tablename
	   * @param wherepart
	   * @throws Exception
	   */
	  public void deleteData(String tablename, String wherepart ) throws Exception {
		    try {
			      // This will load the MySQL driver, each DB has its own driver
			      Class.forName("com.mysql.jdbc.Driver");
			      // Setup the connection with the DB
			      connect = DriverManager.getConnection("jdbc:mysql://" + server_name + "/konto_app?"+"user=" + db_user + "&password=" + db_pwd);

			      // Statements allow to issue SQL queries to the database
			      statement = connect.createStatement();
			      
			      // print query
			      System.out.println("delete from konto_app." + tablename + " where " + wherepart);
			      statement.executeUpdate("delete from konto_app." + tablename + " where " + wherepart);

			    } catch (Exception e) {
			      throw e;
			    } finally {
			      //close();
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

		  // You need to close the resultSet
		  private void close() {
		    try {

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
