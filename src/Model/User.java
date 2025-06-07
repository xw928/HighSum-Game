package Model;
import Helper.*;

abstract public class User {

	private String loginName;
	private String hashPassword;
	
	public User(String loginName, String password) {
		this.loginName = loginName;
		this.hashPassword = Utility.getHash(password);
	}

	public String getLoginName() {
		return this.loginName;
	}
	
	public boolean checkPassword(String password) {
		return this.hashPassword.equals(Utility.getHash(password));
	}
}
