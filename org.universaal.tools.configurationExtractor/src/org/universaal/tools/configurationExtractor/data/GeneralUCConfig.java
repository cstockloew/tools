package org.universaal.tools.configurationExtractor.data;

import java.util.Set;
import java.util.UUID;

/**
 * GeneralUCConfig contains general configuration data for the usecase
 * 
 * @author schwende
 */
public class GeneralUCConfig extends ConfigItem {
	
	public static final String UC_NAME = "ucname";
	public static final String VERSION_NR = "versionnr";
	public static final String AUTHOR = "author";
	public static final String UID = "uid";

	private String versionNr = "", ucName = "", author = "", uid = "";
	
	public GeneralUCConfig() {
	}

	public String getVersionNr() {
		return versionNr;
	}

	public void setVersionNr(String versionNr) {
		this.versionNr = versionNr;
	}

	public String getUcName() {
		return ucName;
	}

	public void setUcName(String ucName) {
		this.ucName = ucName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getUid() {
		if (uid == null || uid.isEmpty()) {
			uid = UUID.randomUUID().toString();
		}
		return uid;
	}

	@Override public boolean setParameter(String param, String value) {
		if (super.setParameter(param, value)) {
			return true;
		}

		if (param.equals(UC_NAME)) {
			setUcName(value);
		} else if (param.equals(VERSION_NR)) {
			setVersionNr(value);
		} else if (param.equals(AUTHOR)) {
			setAuthor(value);
		} else {
			return false;
		}
		
		return true;
	}

	@Override public String getParameter(String param) {
		String s = super.getParameter(param);
		if (s != null) {
			return s;
		}
		
		if (param.equals(UC_NAME)) {
			return getUcName();
		} else if (param.equals(VERSION_NR)) {
			return getVersionNr();
		} else if (param.equals(AUTHOR)) {
			return getAuthor();
		} else if (param.equals(UID)) {
			return getUid();
		} else {
			return null;
		}
	}

	/**
	 * @return the trimmed uc-name that only contains word characters
	 */
	public String getUcNameTrimmed() {
		return ucName.replaceAll("\\W", "");
	}

	/**
	 * @return the trimmed version nr, without spaces
	 */
	public String getVersionNrTrimmed() {
		return versionNr.replaceAll(" ", "");
	}
	
	/**
	 * @return the 3 general config parameters
	 */
	public static String[] getParameters() {
		return new String[] {UC_NAME, VERSION_NR, AUTHOR};
	}
	
	@Override public String validate(Set<String> varNames) {
		return null; // nothing to validate here
	}

}
