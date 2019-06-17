package com.mkyong.validators;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@SuppressWarnings("rawtypes")
@Component
public class BeanValidator implements Validator, InitializingBean {

	private javax.validation.Validator validator;

	public void afterPropertiesSet() throws Exception {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.usingContext().getValidator();
	}

	public void validate(Object target) throws ValidationException {

		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(target);
		System.out.println("Validation errors size (" + constraintViolations.size());
		if (constraintViolations.size() > 0) {
			buildValidationException(constraintViolations);
		}
	}

	public List<String> validateObject(Object target) throws ValidationException {
		List<String> errors = new ArrayList<String>();
		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(target);
		
		
		if (constraintViolations.size() > 0) {
			for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
				errors.add(constraintViolation.getMessage());
			}
		}
		return errors;
	}

	private void buildValidationException(Set<ConstraintViolation<Object>> constraintViolations) {
		StringBuilder message = new StringBuilder();

		for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
			message.append(constraintViolation.getMessage() + "\n");
		}
		System.out.println("Validation errors size (" + constraintViolations.size() + ":::"  +  message);

		throw new ValidationException(message.toString());
	}
}