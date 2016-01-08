package konto.DBUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/** 
 * Main Class to handle to communication between BackEnd and DB
 * @author lpichle
 *
 */
public class DBCommunicator {
    protected Connection connect = null;
    protected Statement statement = null;
    private PreparedStatement preparedStatement = null;
    public ResultSet resultSet = null;

    // connection information
    String server_name = "192.168.1.248";
    String db_user = "konto";
    String db_pwd = "konto";

    DBCommunicator() {
	// initialize connection
	connectDB();
    }
    
    public void connectDB() {
	try {
	    // check if connection is already open (increase overall performance)
	    if (connect == null) {
		// This will load the MySQL driver, each DB has its own driver
		Class.forName("com.mysql.jdbc.Driver");
		// Setup the connection with the DB
		connect = DriverManager.getConnection(
		    "jdbc:mysql://" + server_name + "/konto_app?" + "user=" + db_user + "&password=" + db_pwd);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
    
    public void createStatement() {
	try {
	    statement = connect.createStatement();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    public ResultSet getData(String tableName, String selectPart, String wherePart) throws Exception {
	try {
	    createStatement();
	    // get the result of the SQL query
	    resultSet = statement
		    .executeQuery("select " + selectPart + " from konto_app." + tableName + " " + wherePart);
	    return resultSet;
	} catch (Exception e) {
	    throw e;
	} finally {
	}
    }

    /**
     * Function to insert new Data to DB
     * 
     * @param tableName
     * @param valuePart
     * @param columnPart
     * @throws Exception
     */
    public void insertData(String tableName, String valuePart, String columnPart) throws Exception {
	try {
	    createStatement();
	    System.out.println(
		    "insert into konto_app." + tableName + "( " + columnPart + " ) values( " + valuePart + " )");
	    statement.executeUpdate(
		    "insert into konto_app." + tableName + "( " + columnPart + " ) values( " + valuePart + " )");
	} catch (Exception e) {
	    throw e;
	} finally {
	}
    }

    public ResultSet insertDataPrepared(String tableName, String valuePart, String columnPart) throws Exception {
	try {
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
     * 
     * @param tableName
     * @param setPart
     * @param wherePart
     * @throws Exception
     */
    public void updateData(String tableName, String setPart, String wherePart) throws Exception {
	try {
	    createStatement();
	    System.out.println("update konto_app." + tableName + " set " + setPart + " where " + wherePart);
	    statement.executeUpdate("update konto_app." + tableName + " set " + setPart + " where " + wherePart);
	} catch (Exception e) {
	    throw e;
	} finally {
	}
    }

    /**
     * Function to delete data from DB
     * 
     * @param tableName
     * @param wherePart
     * @throws Exception
     */
    public void deleteData(String tableName, String wherePart) throws Exception {
	try {
	    createStatement();
	    System.out.println("delete from konto_app." + tableName + " where " + wherePart);
	    statement.executeUpdate("delete from konto_app." + tableName + " where " + wherePart);
	} catch (Exception e) {
	    throw e;
	} finally {
	}
    }
    
    public Connection getConnection() {
	return connect;
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
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
    
    public void closeConnection() {
	try {
	    if (connect != null) {
		connect.close();
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
