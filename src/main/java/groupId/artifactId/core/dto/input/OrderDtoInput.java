package groupId.artifactId.core.dto.input;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@Data
@Jacksonized
public class OrderDtoInput {
    @Valid
    @NotNull(message = "selected items cannot be null")
    private final @NonNull List<SelectedItemDtoInput> selectedItems;
}
