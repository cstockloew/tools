package org.universAAL.ucc.startup.api.impl;

import java.io.File;
import java.util.List;

import org.universAAL.ucc.startup.api.Setup;
import org.universAAL.ucc.startup.model.UccUsers;
import org.universAAL.ucc.startup.model.UserAccountInfo;
import javax.xml.bind.JAXB;

public class SetupImpl implements Setup {

	public void saveUsers(List<UserAccountInfo> users, String file) {
		UccUsers all = new UccUsers();
		all.setUser(users);
		JAXB.marshal(all, new File(file));
	}

	public List<UserAccountInfo> getUsers(String file) {
		UccUsers users = JAXB.unmarshal(new File(file), UccUsers.class);
		return users.getUser();
	}

	public void  deleteUser(UserAccountInfo user, String file) {
		// TODO Auto-generated method stub
	}

	public void updateUser(UserAccountInfo user, String file) {
		// TODO Auto-generated method stub
	}

	public void deleteAllUsers(String file) {
		// TODO Auto-generated method stub
	}

	public void saveUser(UserAccountInfo user, String file) {
		List<UserAccountInfo> temp = getUsers(file);
		if(temp != null && !temp.contains(user)) {
			temp.add(user);
		}
		saveUsers(temp, file);

	}

}
