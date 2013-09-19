package org.universaal.tools.packaging.tool.validators;

import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;

public class AlphabeticV implements VerifyListener {

	public void verifyText(VerifyEvent e) {

		e.doit = 
				(Character.isLetter(e.character) || 
						Character.isDigit(e.character) || 
						e.character == '-' || 
						e.character == '\b' || 
						e.character == '.' || 
						e.character == ' ' || 
						e.character == ',' ||
						e.character == ':') && 
						(e.character != '<' && 
						e.character != '>');
	}
}