/*
	Copyright 2007-2014 Fraunhofer IGD, http://www.igd.fraunhofer.de
	Fraunhofer-Gesellschaft - Institut für Graphische Datenverarbeitung
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
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
