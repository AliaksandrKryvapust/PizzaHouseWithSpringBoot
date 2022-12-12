package groupId.artifactId.service.api;

import groupId.artifactId.core.dto.input.OrderDataDtoInput;
import groupId.artifactId.dao.entity.OrderData;

public interface IOrderDataService extends IService<OrderData> {
    void create(OrderDataDtoInput dtoInput);
    OrderData saveInTransaction(OrderDataDtoInput dtoInput);
    OrderData findOrderDataByTicketId(Long id);
}
