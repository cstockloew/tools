package org.universAAL.ucc.database.preferences;

import org.universAAL.ucc.model.preferences.Preferences;

public interface UserAccountDB {
    public void saveStoreAccessData(Preferences pref, String filepath);

    public Preferences getPreferencesData(String file);

    public void updatePreferencesData();
}
