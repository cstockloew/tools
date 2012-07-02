package org.universAAL.tools.makrorecorder.makrorecorder;

import org.osgi.framework.BundleContext;
import org.osgi.service.log.LogService;

import org.slf4j.LoggerFactory;

public class myLogger {
	
//	static LogUtils log;
	static LogService log;
	public static  org.slf4j.Logger logger = LoggerFactory.getLogger(myLogger.class);
	
	public myLogger(BundleContext context) {
		log = (LogService) context.getService(
				context.getServiceReference(LogService.class.getName()));
	}
	
	public void info(String info){
		log.log(LogService.LOG_INFO, info);
//		log.logInfo(logger, this.getClass().toString(), "info", new Object[]{info}, null);
	}
	
	public void debug(String debug) {
		log.log(LogService.LOG_DEBUG, debug);
//		log.logDebug(logger, this.getClass().toString(), "debug",new Object[]{debug}, null);
	}
	
	public void error(String error) {
		log.log(LogService.LOG_ERROR, error);
//		log.logError(logger, this.getClass().toString(), "error",new Object[]{error}, null);
	}

}
