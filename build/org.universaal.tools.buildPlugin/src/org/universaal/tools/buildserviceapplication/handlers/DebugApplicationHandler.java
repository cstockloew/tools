package org.universaal.tools.buildserviceapplication.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.universaal.tools.buildserviceapplication.actions.DebugAction;


public class DebugApplicationHandler extends AbstractHandler{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		DebugAction debugAction=new DebugAction();
		debugAction.run(null);		
		return null;
	}

}
