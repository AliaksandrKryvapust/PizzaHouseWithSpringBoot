package groupId.artifactId.core.dto.input;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Builder
@Data
@Jacksonized
public class SelectedItemDtoInput {
    @NotNull(message = "menu item cannot be null")
    @Positive(message = "menu item should not be less than 0")
    private final Long menuItemId;
    @NotNull(message = "count cannot be null")
    @Positive(message = "count should not be less than 0")
    private final Integer count;
}
