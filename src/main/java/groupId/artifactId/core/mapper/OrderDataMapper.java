package groupId.artifactId.core.mapper;

import groupId.artifactId.core.dto.output.OrderDataDtoOutput;
import groupId.artifactId.core.dto.output.OrderStageDtoOutput;
import groupId.artifactId.core.dto.output.crud.OrderDataDtoCrudOutput;
import groupId.artifactId.dao.entity.OrderData;
import groupId.artifactId.dao.entity.OrderStage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OrderDataMapper {
    private final OrderStageMapper orderStageMapper;

    @Autowired
    public OrderDataMapper(OrderStageMapper orderStageMapper) {
        this.orderStageMapper = orderStageMapper;
    }

    public OrderDataDtoCrudOutput outputCrudMapping(OrderData orderData) {
        return OrderDataDtoCrudOutput.builder()
                .id(orderData.getId())
                .ticketId(orderData.getTicket().getId())
                .done(orderData.getDone())
                .createdAt(orderData.getCreationDate())
                .build();
    }

    public OrderDataDtoOutput outputMapping(OrderData orderData) {
        List<OrderStageDtoOutput> stageDtoOutputs = new ArrayList<>();
        for (OrderStage stage : orderData.getOrderHistory()) {
            OrderStageDtoOutput output = orderStageMapper.outputMapping(stage);
            stageDtoOutputs.add(output);
        }
        return OrderDataDtoOutput.builder()
                .ticketId(orderData.getTicket().getId())
                .orderHistory(stageDtoOutputs)
                .id(orderData.getId())
                .done(orderData.getDone())
                .createdAt(orderData.getCreationDate())
                .build();
    }
}
