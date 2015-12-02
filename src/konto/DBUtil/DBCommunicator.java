package konto.DBUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBCommunicator {
	  protected Connection connect = null;
	  protected Statement statement = null;
	  private PreparedStatement preparedStatement = null;
	  public ResultSet resultSet = null;
	  
	  // connection information
	  String server_name = "192.168.1.248";
	  String db_user="konto";
	  String db_pwd="konto";
	  
	  
	  public void connectDB() {
		  try {
		      // This will load the MySQL driver, each DB has its own driver
		      Class.forName("com.mysql.jdbc.Driver");
		      // Setup the connection with the DB
		      connect = DriverManager.getConnection("jdbc:mysql://" + server_name + "/konto_app?"+"user=" + db_user + "&password=" + db_pwd);
	
		      // Statements allow to issue SQL queries to the database
		      statement = connect.createStatement();
		  } catch(Exception e) {
			  e.printStackTrace();
		  }
	  }
	  
	  public ResultSet getData(String tableName, String selectPart, String wherePart ) throws Exception {
		    try {
		    	connectDB();		      
			    // Result set get the result of the SQL query
			    resultSet = statement.executeQuery("select " + selectPart + " from konto_app." + tableName + " " + wherePart);
			    return resultSet;
		    } catch (Exception e) {
		    	throw e;
			} finally {
			}
	  }
	  
	  /**
	   * Function to insert new Data to DB
	   * @param tableName
	   * @param valuePart
	   * @param columnPart
	   * @throws Exception
	   */
	  public void insertData(String tableName, String valuePart, String columnPart ) throws Exception {
		    try {
		    	connectDB();
			    System.out.println("insert into konto_app." + tableName + "( " + columnPart + " ) values( " + valuePart + " )");
			    statement.executeUpdate("insert into konto_app." + tableName + "( " + columnPart + " ) values( " + valuePart + " )");
		    } catch (Exception e) {
		    	throw e;
		    } finally {
		    }
	  }
	  
	  public ResultSet insertDataPrepared(String tableName, String valuePart, String columnPart ) throws Exception {
		    try {
		    	connectDB();
			    String pSql = "insert into konto_app." + tableName + "( " + columnPart + " ) values( " + valuePart + " )";
			    preparedStatement = connect.prepareStatement((pSql), Statement.RETURN_GENERATED_KEYS);
			    preparedStatement.executeUpdate();
				    
				// get generated keys from query
				this.resultSet = preparedStatement.getGeneratedKeys();
			    return resultSet;
		    } catch (Exception e) {
		    	throw e;
		    } finally {
		    }
	  }
	  
	  
	  /**
	   * Function to update data in DB
	   * @param tableName
	   * @param setPart
	   * @param wherePart
	   * @throws Exception
	   */
	  public void updateData(String tableName, String setPart, String wherePart ) throws Exception {
		    try {
		    	connectDB();
			    System.out.println("update konto_app." + tableName + " set " + setPart + " where " + wherePart);
			    statement.executeUpdate("update konto_app." + tableName + " set " + setPart + " where " + wherePart);
		    } catch (Exception e) {
		    	throw e;
		    } finally {
			}
	  }
	  
	  /**
	   * Function to delete data from DB
	   * @param tableName
	   * @param wherePart
	   * @throws Exception
	   */
	  public void deleteData(String tableName, String wherePart ) throws Exception {
		    try {
		    	connectDB();
			    System.out.println("delete from konto_app." + tableName + " where " + wherePart);
			    statement.executeUpdate("delete from konto_app." + tableName + " where " + wherePart);
		    } catch (Exception e) {
		    	throw e;
			} finally {
			}
	  }
	  
		// close the resultSet and connection
		protected void close() {
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
				e.printStackTrace();
			}	
	    }
}
