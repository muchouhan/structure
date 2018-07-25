package com.mukund.structure.validators;

import com.mukund.structure.model.DroolsBookFacts;

public abstract class BaseValidator {
	
	public abstract void validate(DroolsBookFacts fact);

	public void fieldMandatory(String fieldName,DroolsBookFacts facts) {
		System.out.println(fieldName+ " Field is Mandatory");
		facts.getErrors().put(fieldName, "This Field is Mandatory");
	}
	
}
