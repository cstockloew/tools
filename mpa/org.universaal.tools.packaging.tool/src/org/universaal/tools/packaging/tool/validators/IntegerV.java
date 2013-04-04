package org.universaal.tools.packaging.tool.validators;

import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;

public class IntegerV implements VerifyListener {

	public void verifyText(VerifyEvent e) {

		e.doit = Character.isDigit(e.character) || e.character == '\b' || e.character == '.';
	}
}