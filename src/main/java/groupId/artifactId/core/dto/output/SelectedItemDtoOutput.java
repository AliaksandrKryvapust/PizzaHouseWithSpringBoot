package groupId.artifactId.core.dto.output;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.time.Instant;

@Builder
@Getter
@ToString
public class SelectedItemDtoOutput {
    private final @NonNull Long menuItemId;
    private final @NonNull Long id;
    private final @NonNull Integer count;
    private final @NonNull Instant createdAt;
}
