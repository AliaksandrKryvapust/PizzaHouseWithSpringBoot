package groupId.artifactId.service;

import groupId.artifactId.core.dto.input.OrderDataDtoInput;
import groupId.artifactId.core.mapper.OrderStageMapper;
import groupId.artifactId.dao.OrderDataDao;
import groupId.artifactId.dao.entity.*;
import groupId.artifactId.dao.entity.api.IOrderData;
import groupId.artifactId.dao.entity.api.IOrderStage;
import groupId.artifactId.dao.entity.api.ISelectedItem;
import groupId.artifactId.dao.entity.api.ITicket;
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

import static groupId.artifactId.core.Constants.ORDER_FINISH_DESCRIPTION;
import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class OrderDataServiceTest {
    @InjectMocks
    private OrderDataService orderDataService;
    @Mock
    private OrderDataDao orderDataDao;
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
        List<ISelectedItem> selectedItems = singletonList(SelectedItem.builder().id(id).menuItem(menuItem).count(count)
                .createAt(creationDate).build());
        final Order order = new Order(id, selectedItems);
        final ITicket ticket = new Ticket(id, order, creationDate);
        final OrderDataDtoInput orderDataDtoInput = OrderDataDtoInput.builder().ticketId(id).description(ORDER_FINISH_DESCRIPTION).build();
        IOrderStage stage = OrderStage.builder().description(description).id(id).creationDate(creationDate).build();
        List<IOrderStage> stages = new ArrayList<>();
        stages.add(stage);
        final IOrderData orderData = OrderData.builder().id(id).orderHistory(stages).done(done).ticket(ticket)
                .creationDate(creationDate).build();
        Mockito.when(orderStageMapper.inputMapping(any(String.class))).thenReturn(stage);
        Mockito.when(orderDataDao.get(any(Long.class))).thenReturn(orderData);
        Mockito.when(orderDataDao.update(any(IOrderData.class))).thenReturn(orderData);

        //test
        IOrderData test = orderDataService.saveInTransaction(orderDataDtoInput);

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
        List<ISelectedItem> selectedItems = singletonList(SelectedItem.builder().id(id).menuItem(menuItem).count(count)
                .createAt(creationDate).build());
        final Order order = new Order(id, selectedItems);
        final ITicket ticket = new Ticket(id, order, creationDate);
        final OrderDataDtoInput orderDataDtoInput = OrderDataDtoInput.builder().ticketId(id).ticket(ticket).description(stageDescription).build();
        IOrderStage stages = OrderStage.builder().description(description).id(id).creationDate(creationDate).build();
        final IOrderData orderData = OrderData.builder().id(id).orderHistory(singletonList(stages)).done(done).ticket(ticket)
                .creationDate(creationDate).build();
        Mockito.when(orderStageMapper.inputMapping(any(String.class))).thenReturn(stages);
        Mockito.when(orderDataDao.save(any(IOrderData.class))).thenReturn(orderData);
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
        List<ISelectedItem> selectedItems = singletonList(SelectedItem.builder().id(id).menuItem(menuItem).count(count)
                .createAt(creationDate).build());
        final Order order = new Order(id, selectedItems);
        final ITicket ticket = new Ticket(id, order, creationDate);
        List<IOrderStage> orderStages = singletonList(OrderStage.builder().id(id).description(stageDescription)
                .creationDate(creationDate).build());
        IOrderStage stages = OrderStage.builder().description(description).id(id).creationDate(creationDate).build();
        final IOrderData orderData = OrderData.builder().id(id).orderHistory(singletonList(stages)).done(done).ticket(ticket)
                .creationDate(creationDate).orderHistory(orderStages).build();
        Mockito.when(orderDataDao.get()).thenReturn(singletonList(orderData));

        //test
        List<IOrderData> test = orderDataService.get();

        // assert
        Assertions.assertEquals(singletonList(orderData).size(), test.size());
        for (IOrderData output : test) {
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
        List<ISelectedItem> selectedItems = singletonList(SelectedItem.builder().id(id).menuItem(menuItem).count(count)
                .createAt(creationDate).build());
        final Order order = new Order(id, selectedItems);
        final ITicket ticket = new Ticket(id, order, creationDate);
        List<IOrderStage> orderStages = singletonList(OrderStage.builder().id(id).description(stageDescription)
                .creationDate(creationDate).build());
        IOrderStage stages = OrderStage.builder().description(description).id(id).creationDate(creationDate).build();
        final IOrderData orderData = OrderData.builder().id(id).orderHistory(singletonList(stages)).done(done).ticket(ticket)
                .creationDate(creationDate).orderHistory(orderStages).build();
        Mockito.when(orderDataDao.get(id)).thenReturn(orderData);

        //test
        IOrderData test = orderDataService.get(id);

        // assert
        Assertions.assertNotNull(test);
        Assertions.assertEquals(id, test.getId());
        Assertions.assertEquals(done, test.getDone());
        Assertions.assertEquals(creationDate, test.getCreationDate());
    }
}