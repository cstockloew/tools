package org.universaal.tools.packaging.tool.validators;

import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;

public class UriV implements VerifyListener {

	public void verifyText(VerifyEvent e) {

		//		com.sun.jersey.api.uri.UriPattern;
		//
		//		String regex = "[ab]{4,6}c";
		//		Xeger generator = new Xeger(Xeger.);
		//		String result = generator.generate();
		//		assert result.matches(regex);
		e.doit = true;
	}
}