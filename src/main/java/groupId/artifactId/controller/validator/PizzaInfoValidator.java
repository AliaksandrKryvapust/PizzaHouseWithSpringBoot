package groupId.artifactId.controller.validator;

import groupId.artifactId.controller.validator.api.IPizzaInfoValidator;
import groupId.artifactId.core.dto.input.PizzaInfoDtoInput;
import org.springframework.stereotype.Component;

import javax.validation.ValidationException;

@Component
class PizzaInfoValidator implements IPizzaInfoValidator {
    public PizzaInfoValidator() {
    }

    @Override
    public void validate(PizzaInfoDtoInput pizzaInfo) {
        if (pizzaInfo.getName().isBlank()) {
            throw new ValidationException("Pizza`s name is not valid");
        }
        if (pizzaInfo.getDescription().isBlank()) {
            throw new ValidationException("Pizza`s description is not valid");
        }
        if (pizzaInfo.getSize() <= 0) {
            throw new ValidationException("Pizza`s size is not valid");
        }
    }
}
