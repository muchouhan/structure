package com.mukund.structure.validators;

import com.mukund.structure.model.DroolsBookFacts;

public class BookNameValidator extends BaseValidator{

	@Override
	public void validate(DroolsBookFacts facts) {
		System.out.println("Inside LastNameValidator rule"+facts);
	}
	
	

}
