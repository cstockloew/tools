package org.universAAL.ucc.startup.api;

import java.util.List;

import org.universAAL.ucc.startup.model.User;

public interface Setup {
	public void saveUsers(List<User>users, String file);
	public List<User> getUsers(String file);
	public void deleteUser(User user, String file);
	public void updateUser(User user, String file);
	public void deleteAllUsers(String file);
	public void saveUser(User user, String file);
}
