package org.universaal.tools.packaging.tool.util;

import org.universaal.tools.packaging.tool.preferences.EclipsePreferencesConfigurator;

public class DefaultLogger {
	private static DefaultLogger logger = null;
	
	private DefaultLogger(){}
	
	public static synchronized DefaultLogger getInstance(){
		if(logger == null) logger = new DefaultLogger();
		return logger;
	}
	
	public void log(String message){
		log(message, 0);
	}
	
	public void log(String message, int level){
		if(EclipsePreferencesConfigurator.local.isConsoleLog() && level >= EclipsePreferencesConfigurator.local.getLogLevel()){
			System.out.println(message);
		}
	}
	
}
