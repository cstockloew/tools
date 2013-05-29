package org.universaal.tools.uStoreClientapplication.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.universaal.tools.uStoreClientapplication.actions.PublishAction;


public class PublishApplicationHandler extends AbstractHandler{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		String filePathParam = event.getParameter("org.universaal.tools.uStoreClienteapplication.filePathParameter"); 
		PublishAction publishAction=new PublishAction(filePathParam);
		publishAction.run(null);		
		return null;
	}

}
