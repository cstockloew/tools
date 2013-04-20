package org.universAAL.ucc.startup.api.impl;

import java.io.File;
import java.util.List;

import org.universAAL.ucc.startup.api.Setup;
import org.universAAL.ucc.startup.model.UccUsers;
import org.universAAL.ucc.startup.model.User;
import javax.xml.bind.JAXB;

public class SetupImpl implements Setup {

	public void saveUsers(List<User> users, String file) {
		UccUsers all = new UccUsers();
		all.setUser(users);
		JAXB.marshal(all, new File(file));
	}

	public List<User> getUsers(String file) {
		UccUsers users = JAXB.unmarshal(new File(file), UccUsers.class);
		return users.getUser();
	}

	public void  deleteUser(User user, String file) {
		// TODO Auto-generated method stub
	}

	public void updateUser(User user, String file) {
		// TODO Auto-generated method stub
	}

	public void deleteAllUsers(String file) {
		// TODO Auto-generated method stub
	}

	public void saveUser(User user, String file) {
		List<User> temp = getUsers(file);
		if(temp != null && !temp.contains(user)) {
			temp.add(user);
		}
		saveUsers(temp, file);

	}

}
