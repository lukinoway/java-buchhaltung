package konto.data.DBUtil;

import konto.data.container.UserContainer;
import konto.data.model.LoginUser;

/**
 * main interface for user operations
 * @author lukas
 *
 */
public interface IUser {
	
	public void createUser(LoginUser user);
	
	public boolean validateLogin(LoginUser user);
	
	public void loadUserId(LoginUser user);
	
	public void changePwd(LoginUser user);
	
	public UserContainer getUsers();
	
}
