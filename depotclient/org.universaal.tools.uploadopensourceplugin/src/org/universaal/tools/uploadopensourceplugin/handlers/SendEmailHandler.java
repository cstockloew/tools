package org.universaal.tools.uploadopensourceplugin.handlers;

import java.awt.Desktop;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.universaal.tools.uploadopensourceplugin.email.SendEmail;


public class SendEmailHandler extends AbstractHandler {
	
	Desktop desktop;
	
	public SendEmailHandler(){
		
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		SendEmail email = new SendEmail();
		email.sendEmail();
	    
		return null;
	}

}
