package org.universaal.tools.conformanceTools.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.universaal.tools.conformanceTools.windows.MainFrame;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class ConformanceToolsHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public ConformanceToolsHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {

		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		MainFrame.draw(window, event);

		return null;
	}
}