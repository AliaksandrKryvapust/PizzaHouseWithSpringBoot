package groupId.artifactId.core.dto.input;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@Data
@Jacksonized
public class MenuDtoInput {
    @NotBlank(message = "name cannot be empty")
    private final String name;
    @NotNull(message = "enable cannot be null")
    private final Boolean enable;
    private final List<MenuItemDtoInput> items;
}
