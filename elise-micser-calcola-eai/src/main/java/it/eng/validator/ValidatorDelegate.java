package it.eng.validator;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ValidatorDelegate implements Validator {

	private Map<String, AppValidator> beanValidatorMap;

	@Autowired
	protected ApplicationContext ac;

	@PostConstruct
	public void init () {

		beanValidatorMap = ac.getBeansOfType(AppValidator.class);
	}


	@PreDestroy
	public void destroy () {

		beanValidatorMap.clear();
		beanValidatorMap = null;
	}

	@Override
	public boolean supports (Class<?> clazz) {

		return true;
	}

	@Override
	public void validate (Object target, Errors errors) {

		beanValidatorMap.values().forEach(x -> {
			if (x.supports(target.getClass())) {
				x.validate(target, errors);
			}
		});

	}

}
