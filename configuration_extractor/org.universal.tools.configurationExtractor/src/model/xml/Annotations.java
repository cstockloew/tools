package model.xml;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Annotations {
	

	@Retention(RetentionPolicy.RUNTIME)
	public @interface Root {
		String id();
		String useCaseName();
		String title() default "";
		
	}
		
	@Retention(RetentionPolicy.RUNTIME)
	public @interface NormalPanel {

		String title();

	}

	@Retention(RetentionPolicy.RUNTIME)
	public @interface ListPanel {
		String id();
		String label();
		String hoverText() default "";
		int limit() default -1;
		String uRI();
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Element {
		String id();
		enum Type {CHECKBOX,DROPDOWNLIST,LABEL,LISTBOX,MULTISELECTLISTBOX,RADIOBUTTONGROUP,TEXTAREA,TEXTBOX};
		Type type();
		String label();
		String hoverText() default " ";
		String standardValue() default " ";
		String domain();
	}

	

}
