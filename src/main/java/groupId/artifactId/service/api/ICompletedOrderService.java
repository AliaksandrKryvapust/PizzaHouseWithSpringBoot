package groupId.artifactId.service.api;

import groupId.artifactId.dao.entity.CompletedOrder;

public interface ICompletedOrderService extends IService<CompletedOrder> {
    CompletedOrder create(CompletedOrder type);
    CompletedOrder findCompletedOrderByTicketId(Long id);
}
