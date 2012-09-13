package org.universaal.tools.conformanceTools.utils.old;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class OptionsParser {

	public static String get(String key){

		try{
			InputStream prop = new URL("platform:/plugin/org.universaal.tools.conformanceTools/src/org/universaal/tools/conformanceTools/utils/options.properties").openConnection().getInputStream(); 
			BufferedReader a = new BufferedReader(new InputStreamReader(prop));
			String value;
			while((value = a.readLine()) != null){
				String[] line = value.split("=");
				if(line != null && line.length > 0 && line[0].equals(key))
					return line[1];
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}

		return null;
	}
}