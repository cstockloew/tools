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
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.ViewPart;

public class ImportThirdPartyListener implements SelectionListener {

	ViewPart view;

	public ImportThirdPartyListener(ViewPart view) {
		this.view = view;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		IHandlerService handlerService = (IHandlerService)view.getSite().getService(IHandlerService.class);

		try {
			handlerService.executeCommand("org.universaal.importexternalproject.commands.importthirdparty", null);
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NotDefinedException e1) {
			MessageDialog.openInformation(view.getSite().getShell(),
					"Command not defined.",
					"This command was not available. " +
					"Please install AAL Studio Developer Depot Client Plugin.");
			e1.printStackTrace();
		} catch (NotEnabledException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NotHandledException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}



	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub

	}

}
