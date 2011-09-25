package org.universaal.tools.configurationExtractor.commands;

import javax.swing.JOptionPane;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.handlers.HandlerUtil;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import org.universaal.tools.configurationExtractor.pluginActions.StartAction;
import org.universaal.tools.configurationExtractor.view.MainPanels.MainWindow;

/**
 * 
 * @author Ilja
 * This class could be later used for calling CE from outside.
 */
public class StartHandler implements IHandler {

	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// TODO Auto-generated method stub
		
//		JOptionPane.showMessageDialog( null, "CE is starting!!" );
		System.out.println("Configuration Extractor was called from from StartHandler!");
		
		MainWindow mainWindow=new MainWindow("Configuration Extractor!");
		mainWindow.setVisible( true );
		
		return null;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isHandled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub

	}

}
