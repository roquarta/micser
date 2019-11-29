package it.eng.validator;

import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import io.swagger.model.CalcolaPianoRequest;

@Component
public class CalcolaPianoRequestValidator implements AppValidator {

	@Override
	public boolean supports (Class<?> clazz) {

		return clazz.isAssignableFrom(CalcolaPianoRequest.class);
	}

	@Override
	public void validate (Object target, Errors errors) {

		CalcolaPianoRequest dto = (CalcolaPianoRequest) target;
		if (dto.getImportoRichiesto()== null) {
			//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "stringValue", "error.stringValue", "Validation: String cannot be empty/whitespace");
		}

	}

}
