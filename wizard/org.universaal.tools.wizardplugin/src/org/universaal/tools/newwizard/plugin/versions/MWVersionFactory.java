package org.universaal.tools.newwizard.plugin.versions;

public class MWVersionFactory {
    
    /**
     * Returns an implementation of IMWversion for the given version.
     * 
     * @param versionIndex
     *            The version ID of the MW, as represented by IMWVersion
     *            constants.
     * @return The implementation of the right IMWVersion.
     */
    public static IMWVersion getMWVersion(int versionIndex){
	switch (versionIndex) {
	case IMWVersion.VER_030:
	    return new MWVersion030();
	case IMWVersion.VER_100:
	    return new MWVersion100();
	case IMWVersion.VER_110:
	    return new MWVersion110();
	case IMWVersion.VER_120:
	    return new MWVersion120();
	case IMWVersion.VER_130:
	    return new MWVersion130();
	case IMWVersion.VER_200:
	    return new MWVersion200();
	default:
	    return new MWVersion200();
	}
    }
    
    /**
     * Gets the name of the MW version to display.
     * 
     * @param version
     *            MW Version number, as in IMWversion.
     * @return The String with the name.
     */
    public static String getVERname(int version){
	switch (version) {
	case IMWVersion.VER_030:
	    return "0.3.0-SNAPSHOT";
	case IMWVersion.VER_100:
	    return "1.0.0";
	case IMWVersion.VER_110:
	    return "1.1.0";
	case IMWVersion.VER_120:
	    return "1.2.0";
	case IMWVersion.VER_130:
	    return "1.3.0";
	case IMWVersion.VER_200:
	    return "2.0.0";
	default:
	    return "2.0.0";
	}
    }

    /**
     * Get all the MW version names to display.
     * 
     * @return An array of Strings containing the available MW version names in
     *         order.
     */
    public static String[] getAllVERnames() {
	return new String[] { 
		getVERname(IMWVersion.VER_030),
		getVERname(IMWVersion.VER_100), 
		getVERname(IMWVersion.VER_110),
		getVERname(IMWVersion.VER_120), 
		getVERname(IMWVersion.VER_130),
		getVERname(IMWVersion.VER_200),
		};
    }

}
