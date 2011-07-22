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

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.ui.part.ViewPart;

/**
 * A temporary Listener assigned to those arrows where the associated command
 * is not yet implemented.
 * @author Adrian
 *
 */
public class TemporaryMouseListener implements MouseListener {

	ViewPart view;
	String message;
	
	public TemporaryMouseListener(ViewPart view, String input){
		this.view = view;
		this.message = input;
	}
	
	@Override
	public void mouseDoubleClick(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDown(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseUp(MouseEvent arg0) {
		MessageDialog.openInformation(
				view.getSite().getShell(),
				"Sample View",
				"Function not yet implemented: "+message);

	}

}
