package groupId.artifactId.service.api;

import groupId.artifactId.core.dto.output.CompletedOrderDtoOutput;
import groupId.artifactId.core.dto.output.crud.CompletedOrderDtoCrudOutput;
import groupId.artifactId.dao.entity.api.ICompletedOrder;

import javax.persistence.EntityManager;

public interface ICompletedOrderService extends IService<ICompletedOrder> {
    ICompletedOrder create(ICompletedOrder type);
}
