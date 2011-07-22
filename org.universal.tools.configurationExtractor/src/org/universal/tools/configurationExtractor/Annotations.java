package org.universal.tools.configurationExtractor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class Annotations {
	
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Root {
		String name();
		String title();
		String info();
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	public @interface LstPanel {
		String id();
		String title();
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Lst {
		String label();
		String title();
		long limit(); 
		String domain();
		
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	public @interface ElementL {
		String id();
		enum Type {CHECKBOX,DROPDOWNLIST,LABEL,LISTBOX,MULTISELECTLISTBOX,RADIOBUTTONGROUP,TEXTAREA,TEXTBOX};
		Type type();
		String label();
		String title();
		String standardvalue() default " ";
		String domain() default " ";
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	public @interface ElementP {
		String id();
		enum Type {CHECKBOX,DROPDOWNLIST,LABEL,LISTBOX,MULTISELECTLISTBOX,RADIOBUTTONGROUP,TEXTAREA,TEXTBOX};
		Type type();
		String label();
		String title();
		String standardvalue() default " ";
		String domain() default " ";
		
	}

}
