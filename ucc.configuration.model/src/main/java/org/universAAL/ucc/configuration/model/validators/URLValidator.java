package org.universAAL.ucc.configuration.model.validators;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.universAAL.ucc.configuration.model.ConfigOptionRegistry;
import org.universAAL.ucc.configuration.model.configurationinstances.Value;
import org.universAAL.ucc.configuration.model.exceptions.ValidationException;
import org.universAAL.ucc.configuration.model.interfaces.ConfigurationValidator;

public class URLValidator implements ConfigurationValidator {
	
	Logger logger;
	
	public URLValidator() {
		logger = LoggerFactory.getLogger(this.getClass());
	}
	
	
	public boolean isValid(ConfigOptionRegistry registry, Value value) {

		if(value == null || "".equals(value.getValue())){
			return true;
		}
		try {
			int responseCode = getResponseCode(value.getValue());
			logger.debug("response code: " + responseCode);
			if(responseCode == 200){
				return true;
			}
		} catch (MalformedURLException e) {
			logger.error(e.toString());
		} catch (IOException e) {
			logger.error(e.toString());
		}
		return false;
	}

	
	public void validate(ConfigOptionRegistry registry, Value value) throws ValidationException {
		if(!isValid(registry, value)){
			throw new ValidationException("No valid URL");
		}
	}

	
	public void setAttributes(String[] attributes) {
		// TODO Auto-generated method stub
	}
	
	public static int getResponseCode(String urlString) throws MalformedURLException, IOException {
	    URL u = new URL(urlString); 
	    HttpURLConnection huc =  (HttpURLConnection)  u.openConnection(); 
	    huc.setRequestMethod("GET");
	    huc.setConnectTimeout(200);
	    huc.connect(); 
	    return huc.getResponseCode();
	}

}
