package com.letsplay.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.letsplay.service.SignupService;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;
import com.vaadin.data.ValueContext;

@Component
public class UsernameValidator implements Validator<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4724321367337174176L;
	
	@Autowired
	SignupService signupService;
	
	@Override
	public ValidationResult apply(String value, ValueContext context) {
		if (signupService.isUserRegistered(value))
			return ValidationResult.error("....Username taken....");
		else return ValidationResult.ok();
	}

}
