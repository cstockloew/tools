package org.universAAL.ucc.startup.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UccUsers {
	List<UserAccountInfo> user = new ArrayList<UserAccountInfo>();

	@XmlElement(name="user")
		public List<UserAccountInfo> getUser() {
		return user;
	}

	public void setUser(List<UserAccountInfo> allUser) {
		this.user = allUser;
	}
	
}
