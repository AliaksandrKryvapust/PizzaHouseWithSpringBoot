package groupId.artifactId.core.dto.input;

import groupId.artifactId.dao.entity.api.ITicket;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Builder
@Data
@Jacksonized
public class OrderDataDtoInput {
    @NotNull(message = "ticket cannot be null")
    @Positive(message = "ticket should not be less than 0")
    private final Long ticketId;
    @NotBlank(message = "description cannot be empty")
    private final String description;
    private final ITicket ticket;
}
