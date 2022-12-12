package groupId.artifactId.manager;

import groupId.artifactId.core.dto.input.OrderDataDtoInput;
import groupId.artifactId.core.dto.output.OrderDataDtoOutput;
import groupId.artifactId.core.dto.output.OrderStageDtoOutput;
import groupId.artifactId.core.dto.output.crud.OrderDataDtoCrudOutput;
import groupId.artifactId.core.mapper.OrderDataMapper;
import groupId.artifactId.dao.entity.*;
import groupId.artifactId.service.OrderDataService;
import groupId.artifactId.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static groupId.artifactId.core.Constants.ORDER_FINISH_DESCRIPTION;
import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class OrderDataManagerTest {
    @InjectMocks
    private OrderDataManager orderDataManager;
    @Mock
    private OrderDataService orderDataService;
    @Mock
    private OrderDataMapper orderDataMapper;
    @Mock
    private OrderService orderService;

    @Test
    void save() {
        // preconditions
        final long id = 1L;
        final int version = 1;
        final int count = 10;
        final double price = 18.0;
        final String name = "ITALIANO PIZZA";
        final String description = "Mozzarella cheese, basilica, ham";
        final int size = 32;
        final boolean done = false;
        final Instant creationDate = Instant.now();
        final PizzaInfo pizzaInfo = PizzaInfo.builder().name(name).description(description).size(size).build();
        final MenuItem menuItem = MenuItem.builder().id(id).pizzaInfo(pizzaInfo).price(price)
                .creationDate(creationDate).version(version).build();
        List<SelectedItem> selectedItems = singletonList(SelectedItem.builder().id(id).menuItem(menuItem).count(count)
                .createAt(creationDate).build());
        final Order order = new Order(id, selectedItems);
        final Ticket ticket = new Ticket(id, order, creationDate);
        final OrderDataDtoInput orderDataDtoInput = OrderDataDtoInput.builder().ticketId(id).description(ORDER_FINISH_DESCRIPTION).build();
        OrderStage stage = OrderStage.builder().description(description).id(id).creationDate(creationDate).build();
        List<OrderStage> stages = new ArrayList<>();
        stages.add(stage);
        final OrderData orderData = OrderData.builder().id(id).orderHistory(stages).done(done).ticket(ticket)
                .creationDate(creationDate).build();
        final OrderDataDtoCrudOutput dtoOutput = OrderDataDtoCrudOutput.builder().id(id).ticketId(id).done(done)
                .createdAt(creationDate).build();
        Mockito.when(orderService.get(any(Long.class))).thenReturn(ticket);
        Mockito.when(orderDataService.saveInTransaction(any(OrderDataDtoInput.class))).thenReturn(orderData);
        Mockito.when(orderDataMapper.outputCrudMapping(any(OrderData.class))).thenReturn(dtoOutput);

        //test
        OrderDataDtoCrudOutput test = orderDataManager.save(orderDataDtoInput);

        // assert
        Assertions.assertNotNull(test);
        Assertions.assertEquals(id, test.getId());
        Assertions.assertEquals(id, test.getTicketId());
        Assertions.assertEquals(done, test.getDone());
        Assertions.assertEquals(creationDate, test.getCreatedAt());
    }

    @Test
    void get() {
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
        final Order order = new Order(id, selectedItems);
        final Ticket ticket = new Ticket(id, order, creationDate);
        List<OrderStage> orderStages = singletonList(OrderStage.builder().id(id).description(stageDescription)
                .creationDate(creationDate).build());
        OrderStage stages = OrderStage.builder().description(description).id(id).creationDate(creationDate).build();
        final OrderData orderData = OrderData.builder().id(id).orderHistory(singletonList(stages)).done(done).ticket(ticket)
                .creationDate(creationDate).orderHistory(orderStages).build();
        final OrderDataDtoCrudOutput orderDataDtoOutput = OrderDataDtoCrudOutput.builder().id(id).ticketId(id).done(done)
                .createdAt(creationDate).build();
        Mockito.when(orderDataService.get()).thenReturn(singletonList(orderData));
        Mockito.when(orderDataMapper.outputCrudMapping(any(OrderData.class))).thenReturn(orderDataDtoOutput);

        //test
        List<OrderDataDtoCrudOutput> test = orderDataManager.get();

        // assert
        Assertions.assertEquals(singletonList(orderData).size(), test.size());
        for (OrderDataDtoCrudOutput output : test) {
            Assertions.assertNotNull(output);
            Assertions.assertEquals(id, output.getId());
            Assertions.assertEquals(id, output.getTicketId());
            Assertions.assertEquals(done, output.getDone());
            Assertions.assertEquals(creationDate, output.getCreatedAt());
        }
    }

    @Test
    void testGet() {
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
        final Order order = new Order(id, selectedItems);
        final Ticket ticket = new Ticket(id, order, creationDate);
        List<OrderStage> orderStages = singletonList(OrderStage.builder().id(id).description(stageDescription)
                .creationDate(creationDate).build());
        OrderStage stages = OrderStage.builder().description(description).id(id).creationDate(creationDate).build();
        final OrderData orderData = OrderData.builder().id(id).orderHistory(singletonList(stages)).done(done).ticket(ticket)
                .creationDate(creationDate).orderHistory(orderStages).build();
        final OrderDataDtoCrudOutput orderDataDtoOutput = OrderDataDtoCrudOutput.builder().id(id).ticketId(id).done(done)
                .createdAt(creationDate).build();
        Mockito.when(orderDataService.get(id)).thenReturn(orderData);
        Mockito.when(orderDataMapper.outputCrudMapping(any(OrderData.class))).thenReturn(orderDataDtoOutput);

        //test
        OrderDataDtoCrudOutput test = orderDataManager.get(id);

        // assert
        Assertions.assertNotNull(test);
        Assertions.assertEquals(id, test.getId());
        Assertions.assertEquals(id, test.getTicketId());
        Assertions.assertEquals(done, test.getDone());
        Assertions.assertEquals(creationDate, test.getCreatedAt());
    }

    @Test
    void getAllData() {
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
        List<OrderStageDtoOutput> stageDtoOutputs = singletonList(OrderStageDtoOutput.builder().id(id)
                .description(stageDescription).createdAt(creationDate).build());
        final Order order = new Order(id, selectedItems);
        final Ticket ticket = new Ticket(id, order, creationDate);
        final OrderData orderData = OrderData.builder().ticket(ticket).orderHistory(orderStages).id(id).done(done)
                .creationDate(creationDate).build();
        final OrderDataDtoOutput orderDataDtoOutput = OrderDataDtoOutput.builder().ticketId(id)
                .orderHistory(stageDtoOutputs).id(id).done(done).createdAt(creationDate).build();
        Mockito.when(orderDataService.get(id)).thenReturn(orderData);
        Mockito.when(orderDataMapper.outputMapping(any(OrderData.class))).thenReturn(orderDataDtoOutput);

        //test
        OrderDataDtoOutput test = orderDataManager.getAllData(id);

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

    @Test
    void getOrderDataByTicket() {
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
        List<OrderStageDtoOutput> stageDtoOutputs = singletonList(OrderStageDtoOutput.builder().id(id)
                .description(stageDescription).createdAt(creationDate).build());
        final Order order = new Order(id, selectedItems);
        final Ticket ticket = new Ticket(id, order, creationDate);
        final OrderData orderData = OrderData.builder().ticket(ticket).orderHistory(orderStages).id(id).done(done)
                .creationDate(creationDate).build();
        final OrderDataDtoOutput orderDataDtoOutput = OrderDataDtoOutput.builder().ticketId(id)
                .orderHistory(stageDtoOutputs).id(id).done(done).createdAt(creationDate).build();
        Mockito.when(orderDataService.findOrderDataByTicketId(id)).thenReturn(orderData);
        Mockito.when(orderDataMapper.outputMapping(any(OrderData.class))).thenReturn(orderDataDtoOutput);

        //test
        OrderDataDtoOutput test = orderDataManager.getOrderDataByTicket(id);

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