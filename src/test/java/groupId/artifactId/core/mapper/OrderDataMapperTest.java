package groupId.artifactId.core.mapper;

import groupId.artifactId.core.dto.output.*;
import groupId.artifactId.core.dto.output.crud.OrderDataDtoCrudOutput;
import groupId.artifactId.dao.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class OrderDataMapperTest {
    @InjectMocks
    private OrderDataMapper orderDataMapper;
    @Mock
    private OrderStageMapper orderStageMapper;


    @Test
    void outputCrudMapping() {
        // preconditions
        final long id = 1L;
        final boolean done = false;
        final Instant creationDate = Instant.now();
        final Ticket ticket = Ticket.builder().id(id).createAt(creationDate).build();
        final OrderData orderData = OrderData.builder().ticket(ticket).id(id).done(done)
                .creationDate(creationDate).build();

        //test
        OrderDataDtoCrudOutput test = orderDataMapper.outputCrudMapping(orderData);

        // assert
        Assertions.assertNotNull(test);
        Assertions.assertEquals(id, test.getId());
        Assertions.assertEquals(id, test.getTicketId());
        Assertions.assertEquals(done, test.getDone());
        Assertions.assertEquals(creationDate, test.getCreatedAt());
    }

    @Test
    void outputMapping() {
        // preconditions
        final long id = 1L;
        final int version = 1;
        final int count = 10;
        final double price = 18.0;
        final String name = "ITALIANO PIZZA";
        final String description = "Mozzarella cheese, basilica, ham";
        final String stageDescription = "Stage #";
        final int size = 32;
        final boolean done = false;
        final Instant creationDate = Instant.now();
        final PizzaInfo pizzaInfo = PizzaInfo.builder().name(name).description(description).size(size).build();
        final MenuItem menuItem = MenuItem.builder().id(id).pizzaInfo(pizzaInfo).price(price)
                .creationDate(creationDate).version(version).build();
        List<SelectedItem> selectedItems = singletonList(SelectedItem.builder().id(id).menuItem(menuItem).count(count)
                .createAt(creationDate).build());
        List<OrderStage> orderStages = singletonList(OrderStage.builder().id(id).description(stageDescription)
                .creationDate(creationDate).build());
        final OrderStageDtoOutput stageDtoOutputs = OrderStageDtoOutput.builder().id(id)
                .description(stageDescription).createdAt(creationDate).build();
        final Order order = new Order(id, selectedItems);
        final Ticket ticket = new Ticket(id, order, creationDate);
        final OrderData orderData = OrderData.builder().ticket(ticket).orderHistory(orderStages).id(id).done(done)
                .creationDate(creationDate).build();
        Mockito.when(orderStageMapper.outputMapping(any(OrderStage.class))).thenReturn(stageDtoOutputs);

        //test
        OrderDataDtoOutput test = orderDataMapper.outputMapping(orderData);

        // assert
        Assertions.assertNotNull(test);
        Assertions.assertNotNull(test.getOrderHistory());
        Assertions.assertEquals(id, test.getId());
        Assertions.assertEquals(done, test.getDone());
        Assertions.assertEquals(creationDate, test.getCreatedAt());
        Assertions.assertEquals(id, test.getTicketId());
        for (OrderStageDtoOutput output : test.getOrderHistory()) {
            Assertions.assertEquals(id, output.getId());
            Assertions.assertEquals(stageDescription, output.getDescription());
            Assertions.assertEquals(creationDate, output.getCreatedAt());
        }
    }

}