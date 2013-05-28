package org.universAAL.ucc.startup.api;

import java.util.List;

import org.universAAL.ucc.startup.model.UserAccountInfo;

public interface Setup {
	public void saveUsers(List<UserAccountInfo>users, String file);
	public List<UserAccountInfo> getUsers(String file);
	public void deleteUser(UserAccountInfo user, String file);
	public void updateUser(UserAccountInfo user, String file);
	public void deleteAllUsers(String file);
	public void saveUser(UserAccountInfo user, String file);
}
