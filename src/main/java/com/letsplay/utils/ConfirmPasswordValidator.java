package com.letsplay.utils;

import org.springframework.stereotype.Component;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;
import com.vaadin.data.ValueContext;

@Component
public class ConfirmPasswordValidator implements Validator<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2033160414614278518L;
	
	private String passwordToCheckAgainst;
	
	@Override
	public ValidationResult apply(String value, ValueContext context) {
		if (value.equals(passwordToCheckAgainst))
			return ValidationResult.ok();
		else return ValidationResult.error("Password mismatch");
	}
	
	public String getPasswordToCheckAgainst() {
		return passwordToCheckAgainst;
	}
	public void setPasswordToCheckAgainst(String passwordToCheckAgainst) {
		this.passwordToCheckAgainst = passwordToCheckAgainst;
	}

}
