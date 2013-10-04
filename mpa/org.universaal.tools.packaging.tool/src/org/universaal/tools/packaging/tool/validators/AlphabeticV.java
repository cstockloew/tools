package org.universaal.tools.packaging.tool.validators;

import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;

public class AlphabeticV implements VerifyListener {

	public void verifyText(VerifyEvent e) {

		e.doit = e.character == '\b' || e.text == "" || e.text.matches("[A-Za-z0-9.,:\b -]{1,}");
		/*
		 e.doit = true;
				(Character.isLetter(e.character) || 
						Character.isDigit(e.character) || 
						e.character == '-' || 
						e.character == '\b' || 
						e.character == '.' || 
						e.character == ' ' || 
						e.character == ',' ||
						e.character == ':') && 
						(e.character != '<' && 
						e.character != '>');*/
	}
}