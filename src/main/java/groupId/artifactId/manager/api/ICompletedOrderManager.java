package groupId.artifactId.manager.api;

import groupId.artifactId.core.dto.output.CompletedOrderDtoOutput;
import groupId.artifactId.core.dto.output.crud.CompletedOrderDtoCrudOutput;
import groupId.artifactId.dao.entity.CompletedOrder;

public interface ICompletedOrderManager extends IManager<CompletedOrderDtoCrudOutput, CompletedOrder> {
    CompletedOrderDtoOutput getAllData(Long id);
    CompletedOrderDtoOutput getCompletedOrderByTicket(Long id);
}
