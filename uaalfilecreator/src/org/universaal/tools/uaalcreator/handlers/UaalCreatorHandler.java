package org.universaal.tools.uaalcreator.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.universaal.tools.uaalcreator.actions.UaalCreatorAction;

/**
 * This Handler handles the calls to open the ConfigurationExtractor
 * and starts the ExtractorAction
 */
public class UaalCreatorHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		UaalCreatorAction action = new UaalCreatorAction();
		action.run(null);		
		return null;
	}

}
