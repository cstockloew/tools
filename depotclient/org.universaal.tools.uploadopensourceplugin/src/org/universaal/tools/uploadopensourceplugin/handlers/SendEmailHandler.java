/*
	Copyright 2011 SINTEF, http://www.sintef.no
	
	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	  http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
package org.universaal.tools.uploadopensourceplugin.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.universaal.tools.uploadopensourceplugin.email.SendEmail;

/**
 * Handler that handles the "Send Email" command
 * @author Adrian
 *
 */
public class SendEmailHandler extends AbstractHandler {	
	
	public SendEmailHandler(){
		
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		SendEmail email = new SendEmail();
		email.sendEmail();
	    
		return null;
	}

}
