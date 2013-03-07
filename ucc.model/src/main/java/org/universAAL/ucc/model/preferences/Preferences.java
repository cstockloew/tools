package org.universAAL.ucc.model.preferences;

/**
 * The preferences contains an username and a password to login to the ustore. 
 * Also available is the URL to the ustore and 
 * the port on which the ustore is reachable. The preferences define also a language
 * which the user can select to set for his or her ucc.
 * 
 * @author merkle
 *
 */
public class Preferences {
	private String username;
	private String username2;
	private String password;
	private String password2;
	private String shopUrl;
	private String shopUrl2;
	private String port;
	private String language;
	
	public Preferences() { }
	
	public Preferences(String user, String user2, String pwd, String pwd2, String url, String port, String lang) {
		this.username = user;
		this.username2 = user2;
		this.password = pwd;
		this.password2 = pwd2;
		this.shopUrl = url;
		this.port = port;
		this.language = lang;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getShopUrl() {
		return shopUrl;
	}

	public void setShopUrl(String shopUrl) {
		this.shopUrl = shopUrl;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getUsername2() {
		return username2;
	}

	public void setUsername2(String username2) {
		this.username2 = username2;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getShopUrl2() {
		return shopUrl2;
	}

	public void setShopUrl2(String shopUrl2) {
		this.shopUrl2 = shopUrl2;
	}
	
	
	
}
