package konto.data.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginUser implements Serializable {

    private static final long serialVersionUID = 1L;
    private int userId;
    private String userName;
    private String userPass;
    private String userHash;

    public LoginUser(String userName, String userPass) {
	this.userId = 0;
	this.setUserName(userName);
	this.setUserPass(userPass);
	createHash(this.userName + "konto" + this.userPass);

    }
    
    public LoginUser(int userId, String userName) {
	this.userId = userId;
	this.userName = userName;
    }

    public int getUserId() {
	return userId;
    }

    public void setUserId(int userId) {
	this.userId = userId;
    }

    public String getUserName() {
	return userName;
    }

    public void setUserName(String userName) {
	this.userName = userName;
    }

    public String getUserPass() {
	return userPass;
    }

    public void setUserPass(String userPass) {
	this.userPass = userPass;
    }

    public String getUserHash() {
	return userHash;
    }

    public void setUserHash(String userHash) {
	this.userHash = userHash;
    }

    private void createHash(String in_string) {
	try {
	    MessageDigest m = MessageDigest.getInstance("MD5");
	    m.reset();
	    m.update(in_string.getBytes());
	    byte[] digest = m.digest();
	    BigInteger bigInt = new BigInteger(1, digest);
	    String hashtext = bigInt.toString(16);

	    while (hashtext.length() < 32) {
		hashtext = "0" + hashtext;
	    }
	    setUserHash(hashtext);
	} catch (NoSuchAlgorithmException e) {
	    System.out.println("createHash - hier lief was schief: NoSuchAlgorithmException");
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

}
