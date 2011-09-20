package org.universaal.tools.conformance.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.universaal.tools.conformance.actions.ConformanceAction;


public class ConformanceHandler extends AbstractHandler{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ConformanceAction conformanceAction=new ConformanceAction();
		conformanceAction.run(null);		
		return null;
	}

}
