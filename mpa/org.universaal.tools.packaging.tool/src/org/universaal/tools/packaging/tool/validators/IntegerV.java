package org.universaal.tools.packaging.tool.validators;

import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;

public class IntegerV implements VerifyListener {

	public void verifyText(VerifyEvent e) {

		e.doit = e.text=="" || e.text.matches("[0-9.\b]{1,}") || e.character == '\b';
	}
}