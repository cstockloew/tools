package org.universAAL.ucc.configuration.model.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.universAAL.ucc.configuration.model.ConfigOptionRegistry;
import org.universAAL.ucc.configuration.model.configurationinstances.Value;
import org.universAAL.ucc.configuration.model.exceptions.ValidationException;
import org.universAAL.ucc.configuration.model.interfaces.ConfigurationValidator;

public class IntMinMaxValidator implements ConfigurationValidator {
	
	static Logger logger = LoggerFactory.getLogger(IntMinMaxValidator.class);
	int min, max;
	
	
	public boolean isValid(ConfigOptionRegistry registry, Value value) {
		if("".equals(value.getValue())){
			return true;
		}
		try{
			int intValue = Integer.parseInt(value.getValue());
			return intValue <= max && intValue >= min;
		}catch (NumberFormatException e){
			logger.error(e.toString());
		}
		return false;
	}

	
	public void validate(ConfigOptionRegistry registry, Value value) throws ValidationException {
		if(!isValid(registry, value))
		{
			try{
				int intValue = Integer.parseInt(value.getValue());
				if(intValue > max){
					throw new ValidationException("Value is too large! It needs to be less than or equal to " + max);
				}else if(intValue < min){
					throw new ValidationException("Value is too small! It needs to be greater than or equal to " + min);
				}
			}catch (NumberFormatException e){
				logger.error(e.toString());
			}
		}
	}

	
	public void setAttributes(String[] attributes) {
		if(attributes.length > 1){
			try {
				min = Integer.parseInt(attributes[0]);
				max = Integer.parseInt(attributes[1]);
			} catch (NumberFormatException e) {
				logger.error(e.toString());
			}
		}
	}

}
