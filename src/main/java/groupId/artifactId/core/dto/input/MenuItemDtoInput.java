package groupId.artifactId.core.dto.input;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Builder
@Data
@Jacksonized
public class MenuItemDtoInput {
    @NotNull(message = "price cannot be null")
    @Positive(message = "price should not be less than 0")
    private final Double price;
    @NotNull(message = "pizza info cannot be null")
    private final PizzaInfoDtoInput pizzaInfoDtoInput;
    private final Long menuId;
}
