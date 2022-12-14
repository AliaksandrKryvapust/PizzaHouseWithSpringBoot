package groupId.artifactId.core.mapper;

import groupId.artifactId.core.dto.output.OrderStageDtoOutput;
import groupId.artifactId.dao.entity.OrderStage;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OrderStageMapper {
    public OrderStageMapper() {
    }

    public OrderStage inputMapping(String description) {
        return OrderStage.builder().description(description).build();
    }

    public OrderStageDtoOutput outputMapping(OrderStage orderStage) {
        return OrderStageDtoOutput.builder()
                .id(orderStage.getId())
                .description(orderStage.getDescription())
                .createdAt(orderStage.getCreationDate())
                .build();
    }
}
