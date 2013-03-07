package org.universAAL.ucc.database.preferences.impl;

import java.io.File;

import javax.xml.bind.JAXB;

import org.universAAL.ucc.database.preferences.UserAccountDB;
import org.universAAL.ucc.model.preferences.Preferences;

public class UserAccountDBImpl implements UserAccountDB {

	@Override
	public void saveStoreAccessData(Preferences pref, String filepath) {
		JAXB.marshal(pref, new File(filepath));
		
	}

	@Override
	public Preferences getPreferencesData(String file) {
		Preferences pref = JAXB.unmarshal(new File(file), Preferences.class);
		return pref;
	}

	@Override
	public void updatePreferencesData() {
		// TODO Auto-generated method stub
		
	}

}
