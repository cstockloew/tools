package org.universAAL.ucc.model.preferences;

public class Preferences {
	private String username;
	private String password;
	private String shopUrl;
	private String port;
	private String language;
	
	public Preferences() { }
	
	public Preferences(String user, String pwd, String url, String port, String lang) {
		this.username = user;
		this.password = pwd;
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
	
	
}
