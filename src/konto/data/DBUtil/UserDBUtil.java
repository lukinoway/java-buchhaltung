package konto.data.DBUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import konto.data.model.LoginUser;

public class UserDBUtil extends DBCommunicator implements IUser {

	private ResultSet resSet = null;
	private PreparedStatement pStmt = null;
	
	public UserDBUtil() {
		super();
	}
	
	@Override
	public int createUser(LoginUser user) {
		int user_id = 0;
		try {
		String pSql = "insert into db_user(user_name, user_pass_hash) values(?,?)";
		
		pStmt = connect.prepareStatement((pSql), Statement.RETURN_GENERATED_KEYS);
		pStmt.setString(1, user.getUserName());
		pStmt.setString(2, user.getUserHash());
		pStmt.executeUpdate();
		
		resSet = pStmt.getGeneratedKeys();
		resSet.next();
		
		user_id = resSet.getInt(1);
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return user_id;
	}

	@Override
	public boolean validateLogin(LoginUser user) {
		boolean valid = false;
		try {
			String pSql = "select count(1) from  db_user where user_name = ? and user_pass_hash = ?";
			
			pStmt = connect.prepareStatement(pSql);
			pStmt.setString(1, user.getUserName());
			pStmt.setString(2, user.getUserHash());
			
			resSet = pStmt.executeQuery();
			resSet.next();
			
			System.out.println("resultSet:" + resSet.getInt(1));
			if (resSet.getInt(1) == 1) {
				valid = true;
				System.out.println("valid login");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return valid;
	}

	@Override
	public void changePwd(LoginUser user) {
		// TODO Auto-generated method stub
		
	}
	
	public int loadUserId(LoginUser user) {
		int userId = 0;
		try {
			String pSql = "select max(user_id) from  db_user where user_name = ? and user_pass_hash = ?";
			pStmt = connect.prepareStatement(pSql);
			pStmt.setString(1, user.getUserName());
			pStmt.setString(2, user.getUserHash());
			
			resSet = pStmt.executeQuery();
			resSet.next();
			
			if (resSet.getInt(1) > 0) {
				userId = resSet.getInt(1);
			}		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return userId;
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

}
