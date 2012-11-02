package org.universaal.tools.configurationExtractor.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.universaal.tools.configurationExtractor.actions.ExtractorAction;

/**
 * This Handler handles the calls to open the ConfigurationExtractor
 * and starts the ExtractorAction
 */
public class ExtractorHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ExtractorAction action = new ExtractorAction();
		action.run(null);		
		return null;
	}

}
