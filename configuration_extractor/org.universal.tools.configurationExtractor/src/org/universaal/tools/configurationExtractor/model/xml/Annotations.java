package org.universaal.tools.configurationExtractor.model.xml;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/**
 * @author Ilja
 * All use cases have to had this class. It wont direct used in CE 
 */
public class Annotations {
	
	/**
	 * 
	 * This interface defines a root annotation, which involves all abstract information about use case 
	 *
	 */
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Root {
		String id();
		String useCaseName();
		String title() default "";
		
	}
	/**
	 * 	
	 * NormalPanel is a container for elements
	 *
	 */
	@Retention(RetentionPolicy.RUNTIME)
	public @interface NormalPanel {

		String title();

	}
	/**
	 * ListPanel is a special container for special elements
	 *
	 */
	@Retention(RetentionPolicy.RUNTIME)
	public @interface ListPanel {
		String id();
		String label();
		String hoverText() default "";
		int limit() default -1;
		String uRI();
	}
	/**
	 * All special parameter of use case should be defined as elements 
	 *
	 */
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
