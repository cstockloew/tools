/*
	Copyright 2011 SINTEF, http://www.sintef.no
	
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
package org.universaal.tools.dashboard.buttonlisteners;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.ViewPart;
import org.universaal.tools.dashboard.views.DashboardView;

/**
 * Class that calls the Run Project command when that button is pressed on 
 * the Dashboard.
 * @author Adrian
 *
 */
public class CommandCallingListener implements SelectionListener {

	DashboardView view;
	String commandID;
	String featureName;
	
	
	public CommandCallingListener(DashboardView view, String commandID, String featureName) {
		this.view = view;
		this.commandID = commandID;
		this.featureName = featureName;
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {

	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
/**		IHandlerService handlerService2 = view.getViewSite().getPage().
				.getWorkbenchWindow().;
		HandlerUtil.getActiveWorkbenchWindow(arg0);
		
		
		
*/
		IHandlerService handlerService;

		if (view.getPartOfLastSelection() != null) {
			handlerService = (IHandlerService)view.getPartOfLastSelection().getSite().getService(IHandlerService.class);			
		}
		else {
			handlerService = (IHandlerService)view.getSite().getService(IHandlerService.class);			
		}
		
		try {
			handlerService.executeCommand(commandID, null);
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (NotDefinedException e) {
			MessageDialog.openInformation(view.getSite().getShell(),
					"Command not defined.",
					"This command was not available. " +
					"Please install the AAL Studio Feature: " + featureName );
			e.printStackTrace();
		} catch (NotEnabledException e) {
			MessageDialog.openError(view.getSite().getShell(),
					"Command not enabled.",
					"The command is not enabled for the current selection. Please select a valid target.");
		} catch (NotHandledException e) {
			MessageDialog.openError(view.getSite().getShell(),
					"Not a valid selection.",
					"The command could not be performed on the current selection. Please select a valid target.");
			e.printStackTrace();
		}

	}

}
