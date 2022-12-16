package groupId.artifactId.core.mapper;

import groupId.artifactId.core.dto.output.CompletedOrderDtoOutput;
import groupId.artifactId.core.dto.output.PizzaDtoOutput;
import groupId.artifactId.core.dto.output.crud.CompletedOrderDtoCrudOutput;
import groupId.artifactId.dao.entity.CompletedOrder;
import groupId.artifactId.dao.entity.OrderData;
import groupId.artifactId.dao.entity.Pizza;
import groupId.artifactId.dao.entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CompletedOrderMapper {
    private final PizzaMapper pizzaMapper;

    @Autowired
    public CompletedOrderMapper(PizzaMapper pizzaMapper) {
        this.pizzaMapper = pizzaMapper;
    }

    public CompletedOrder inputMapping(OrderData orderData) {
        Ticket ticket = orderData.getTicket();
        List<Pizza> temp = ticket.getOrder().getSelectedItems().stream().map((i) -> Pizza.builder()
                        .name(i.getMenuItem().getPizzaInfo().getName()).size(i.getMenuItem().getPizzaInfo().getSize()).build())
                .collect(Collectors.toList());
        return CompletedOrder.builder().ticket(ticket).ticketId(ticket.getId()).items(temp).build();
    }

    public CompletedOrderDtoCrudOutput outputCrudMapping(CompletedOrder completedOrder) {
        return CompletedOrderDtoCrudOutput.builder()
                .id(completedOrder.getId())
                .ticketId(completedOrder.getTicketId())
                .createdAt(completedOrder.getCreationDate())
                .build();
    }

    public CompletedOrderDtoOutput outputMapping(CompletedOrder completedOrder) {
        List<PizzaDtoOutput> temp = completedOrder.getItems().stream().map(pizzaMapper::outputMapping).collect(Collectors.toList());
        return CompletedOrderDtoOutput.builder()
                .ticketId(completedOrder.getTicketId())
                .items(temp)
                .id(completedOrder.getId())
                .createdAt(completedOrder.getCreationDate())
                .build();
    }
}
