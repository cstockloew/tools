package org.universaal.tools.buildserviceapplication.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.universaal.tools.buildserviceapplication.actions.PublishAction;


public class PublishApplicationHandler extends AbstractHandler{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		PublishAction publishAction=new PublishAction();
		publishAction.run(null);		
		return null;
	}

}
