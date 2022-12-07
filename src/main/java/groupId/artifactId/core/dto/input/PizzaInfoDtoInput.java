package groupId.artifactId.core.dto.input;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Builder
@Data
@Jacksonized
public class PizzaInfoDtoInput {
    @NotBlank(message = "name cannot be empty")
    private final String name;
    @NotBlank(message = "description cannot be empty")
    private final String description;
    @NotNull(message = "size cannot be null")
    @Positive(message = "size should not be less than 0")
    private final Integer size;
}
