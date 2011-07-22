package org.universaal.tools.buildserviceapplication.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.universaal.tools.buildserviceapplication.actions.RunAction;

public class RunApplicationHandler extends AbstractHandler{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		RunAction runAction=new RunAction();
		runAction.run(null);		
		return null;
	}

}
