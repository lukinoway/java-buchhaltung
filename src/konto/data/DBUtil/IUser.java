package konto.data.DBUtil;

import konto.data.model.LoginUser;

/**
 * main interface for user operations
 * @author lukas
 *
 */
public interface IUser {
	
	public int createUser(LoginUser user);
	
	public boolean validateLogin(LoginUser user);
	
	public int loadUserId(LoginUser user);
	
	public void changePwd(LoginUser user);
	
}
