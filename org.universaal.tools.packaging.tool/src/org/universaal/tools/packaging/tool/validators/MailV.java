package org.universaal.tools.packaging.tool.validators;

import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;

public class MailV implements VerifyListener {

	private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
					+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public void verifyText(VerifyEvent e) {

		//		if(e.widget instanceof Text){
		//			if(((Text)e.widget).getText().matches(EMAIL_PATTERN))
		//				e.doit = true;
		//			else
		//				e.doit = false;
		//		}
		e.doit = true;
	}
}