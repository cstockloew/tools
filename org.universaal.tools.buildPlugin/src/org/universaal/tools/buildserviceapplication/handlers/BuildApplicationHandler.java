package org.universaal.tools.buildserviceapplication.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.universaal.tools.buildserviceapplication.actions.BuildAction;

public class BuildApplicationHandler extends AbstractHandler{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		BuildAction build=new BuildAction();
		build.run(null);		
		return null;
	}

}
