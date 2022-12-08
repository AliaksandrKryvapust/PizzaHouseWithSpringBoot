package groupId.artifactId.manager.api;

import groupId.artifactId.core.dto.input.OrderDataDtoInput;
import groupId.artifactId.core.dto.output.OrderDataDtoOutput;
import groupId.artifactId.core.dto.output.crud.OrderDataDtoCrudOutput;

public interface IOrderDataManager extends IManager<OrderDataDtoCrudOutput, OrderDataDtoInput> {
    OrderDataDtoOutput getAllData(Long id);
}
