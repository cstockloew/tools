/*
	Copyright 2007-2014 FZI, http://www.fzi.de
	Forschungszentrum Informatik - Information Process Engineering (IPE)

	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
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

package org.universAAL.ucc.model;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.ucc.api.model.IModel;

/**
 * 
 * @author tzentek - <a href="mailto:zentek@fzi.de">Tom Zentek</a>
 * 
 */


public class Activator implements BundleActivator {
	public static ModuleContext mc;
    public static DefaultServiceCaller sc;
	public static BundleContext context=null;
	
	private static IModel model;

	public void start(BundleContext context) throws Exception {
		mc = uAALBundleContainer.THE_CONTAINER.registerModule(new Object[] { context });
	    sc = new DefaultServiceCaller(mc);
		Activator.context=context;
		
		model = new Model();
		
		context.registerService(new String[] { IModel.class.getName() }, model, null);
	}

	public void stop(BundleContext arg0) throws Exception {
		
	}
	
	public static IModel getModel() {
		return model;
	}

}
