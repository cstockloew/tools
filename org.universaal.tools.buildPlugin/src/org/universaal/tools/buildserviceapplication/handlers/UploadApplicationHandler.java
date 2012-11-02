package org.universaal.tools.buildserviceapplication.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.universaal.tools.buildserviceapplication.actions.UploadAction;


public class UploadApplicationHandler extends AbstractHandler{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		UploadAction uploadAction=new UploadAction();
		uploadAction.run(null);		
		return null;
	}

}
