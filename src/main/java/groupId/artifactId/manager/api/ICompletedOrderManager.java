package groupId.artifactId.manager.api;

import groupId.artifactId.core.dto.output.CompletedOrderDtoOutput;
import groupId.artifactId.core.dto.output.crud.CompletedOrderDtoCrudOutput;
import groupId.artifactId.dao.entity.api.ICompletedOrder;

public interface ICompletedOrderManager extends IManager<CompletedOrderDtoCrudOutput, ICompletedOrder>{
    CompletedOrderDtoOutput getAllData(Long id);
}
