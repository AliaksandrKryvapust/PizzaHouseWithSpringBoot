package groupId.artifactId.manager.api;

import groupId.artifactId.core.dto.input.OrderDtoInput;
import groupId.artifactId.core.dto.output.TicketDtoOutput;
import groupId.artifactId.core.dto.output.crud.TicketDtoCrudOutput;

public interface IOrderManager extends IManager<TicketDtoCrudOutput, OrderDtoInput>{
    TicketDtoOutput getAllData(Long id);
}
