package espot;

/**
 * Convenience class to hold credentials data
 * note: it doesn't persist the credentials
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class CredentialsPojo {

	public String userName;
	public String password;

	public CredentialsPojo() {
	}

	public CredentialsPojo(String inUserName, String inPassword) {
		setCredentials(inUserName, inPassword);
	}

	public void setCredentials(String inUserName, String inPassword) {
		userName = inUserName;
		password = inPassword;
	}
}
