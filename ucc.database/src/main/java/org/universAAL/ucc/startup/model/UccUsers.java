package org.universAAL.ucc.startup.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UccUsers {
	List<User> user = new ArrayList<User>();

	@XmlElement(name="user")
		public List<User> getUser() {
		return user;
	}

	public void setUser(List<User> allUser) {
		this.user = allUser;
	}
	
}
