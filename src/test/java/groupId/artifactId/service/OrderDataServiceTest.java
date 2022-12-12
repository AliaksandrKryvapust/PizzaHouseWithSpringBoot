package groupId.artifactId.service;

import groupId.artifactId.core.dto.input.OrderDataDtoInput;
import groupId.artifactId.core.mapper.OrderStageMapper;
import groupId.artifactId.dao.api.IOrderDataDao;
import groupId.artifactId.dao.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static groupId.artifactId.core.Constants.ORDER_FINISH_DESCRIPTION;
import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class OrderDataServiceTest {
    @InjectMocks
    private OrderDataService orderDataService;
    @Mock
    private IOrderDataDao orderDataDao;
    @Mock
    private OrderStageMapper orderStageMapper;

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
        Mockito.when(orderStageMapper.inputMapping(any(String.class))).thenReturn(stage);
        Mockito.when(orderDataDao.findOrderDataByTicket_Id(any(Long.class))).thenReturn(Optional.of(orderData));
        Mockito.when(orderDataDao.saveAndFlush(any(OrderData.class))).thenReturn(orderData);

        //test
        OrderData test = orderDataService.saveInTransaction(orderDataDtoInput);

        // assert
        Assertions.assertNotNull(test);
        Assertions.assertEquals(id, test.getId());
        Assertions.assertEquals(done, test.getDone());
        Assertions.assertEquals(creationDate, test.getCreationDate());
    }

    @Test
    void create() {
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
        final OrderDataDtoInput orderDataDtoInput = OrderDataDtoInput.builder().ticketId(id).ticket(ticket).description(stageDescription).build();
        OrderStage stages = OrderStage.builder().description(description).id(id).creationDate(creationDate).build();
        final OrderData orderData = OrderData.builder().id(id).orderHistory(singletonList(stages)).done(done).ticket(ticket)
                .creationDate(creationDate).build();
        Mockito.when(orderStageMapper.inputMapping(any(String.class))).thenReturn(stages);
        Mockito.when(orderDataDao.save(any(OrderData.class))).thenReturn(orderData);
        ArgumentCaptor<OrderData> value = ArgumentCaptor.forClass(OrderData.class);

        //test
        orderDataService.create(orderDataDtoInput);
        Mockito.verify(orderDataDao, times(1)).save(value.capture());

        // assert
        Assertions.assertEquals(singletonList(stages), value.getValue().getOrderHistory());
        Assertions.assertEquals(ticket, value.getValue().getTicket());
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
        Mockito.when(orderDataDao.findAll()).thenReturn(singletonList(orderData));

        //test
        List<OrderData> test = orderDataService.get();

        // assert
        Assertions.assertEquals(singletonList(orderData).size(), test.size());
        for (OrderData output : test) {
            Assertions.assertNotNull(output);
            Assertions.assertEquals(id, output.getId());
            Assertions.assertEquals(done, output.getDone());
            Assertions.assertEquals(creationDate, output.getCreationDate());
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
        Mockito.when(orderDataDao.findById(id)).thenReturn(Optional.of(orderData));

        //test
        OrderData test = orderDataService.get(id);

        // assert
        Assertions.assertNotNull(test);
        Assertions.assertEquals(id, test.getId());
        Assertions.assertEquals(done, test.getDone());
        Assertions.assertEquals(creationDate, test.getCreationDate());
    }

    @Test
    void findOrderDataByTicketId() {
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
        Mockito.when(orderDataDao.findOrderDataByTicket_Id(id)).thenReturn(Optional.of(orderData));

        //test
        OrderData test = orderDataService.findOrderDataByTicketId(id);

        // assert
        Assertions.assertNotNull(test);
        Assertions.assertEquals(id, test.getId());
        Assertions.assertEquals(done, test.getDone());
        Assertions.assertEquals(creationDate, test.getCreationDate());
    }
}