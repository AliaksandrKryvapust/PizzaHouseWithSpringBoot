package groupId.artifactId.service.api;

import groupId.artifactId.core.dto.input.OrderDataDtoInput;
import groupId.artifactId.core.dto.output.OrderDataDtoOutput;
import groupId.artifactId.core.dto.output.crud.OrderDataDtoCrudOutput;
import groupId.artifactId.dao.entity.api.IOrderData;

import javax.persistence.EntityManager;

public interface IOrderDataService extends IService<IOrderData> {
    void create(OrderDataDtoInput dtoInput);
    IOrderData saveInTransaction(OrderDataDtoInput dtoInput);
}
