package groupId.artifactId.manager;

import groupId.artifactId.core.dto.output.*;
import groupId.artifactId.core.dto.output.crud.CompletedOrderDtoCrudOutput;
import groupId.artifactId.core.mapper.CompletedOrderMapper;
import groupId.artifactId.dao.entity.*;
import groupId.artifactId.service.CompletedOrderService;
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
class CompletedOrderManagerTest {
    @InjectMocks
    private CompletedOrderManager completedOrderManager;
    @Mock
    private CompletedOrderService completedOrderService;
    @Mock
    private CompletedOrderMapper completedOrderMapper;

    @Test
    void getAllData() {
        // preconditions
        final long id = 1L;
        final int version = 1;
        final int count = 10;
        final double price = 18.0;
        final String name = "ITALIANO PIZZA";
        final String description = "Mozzarella cheese, basilica, ham";
        final int size = 32;
        final Instant creationDate = Instant.now();
        final PizzaInfo pizzaInfo = PizzaInfo.builder().name(name).description(description).size(size).build();
        final MenuItem menuItem = MenuItem.builder().id(id).pizzaInfo(pizzaInfo).price(price)
                .creationDate(creationDate).version(version).build();
        List<SelectedItem> selectedItems = singletonList(SelectedItem.builder().id(id).menuItem(menuItem).count(count)
                .createAt(creationDate).build());
        List<Pizza> pizzas = singletonList(Pizza.builder().id(id).name(name).size(size).build());
        final Order order = new Order(id, selectedItems);
        final Ticket ticket = new Ticket(id, order, creationDate);
        CompletedOrder completedOrder = CompletedOrder.builder().items(pizzas).ticket(ticket).id(id)
                .creationDate(creationDate).build();
        List<PizzaDtoOutput> pizzaDtoOutputs = singletonList(PizzaDtoOutput.builder().id(id)
                .name(name).size(size).build());
        final CompletedOrderDtoOutput dtoOutput = CompletedOrderDtoOutput.builder().ticketId(id)
                .items(pizzaDtoOutputs).id(id).createdAt(creationDate).build();
        Mockito.when(completedOrderService.get(id)).thenReturn(completedOrder);
        Mockito.when(completedOrderMapper.outputMapping(any(CompletedOrder.class))).thenReturn(dtoOutput);

        //test
        CompletedOrderDtoOutput test = completedOrderManager.getAllData(id);

        // assert
        Assertions.assertNotNull(test);
        Assertions.assertNotNull(test.getItems());
        Assertions.assertEquals(id, test.getId());
        Assertions.assertEquals(creationDate, test.getCreatedAt());
        Assertions.assertEquals(id, test.getTicketId());
        for (PizzaDtoOutput output : test.getItems()) {
            Assertions.assertEquals(id, output.getId());
            Assertions.assertEquals(name, output.getName());
            Assertions.assertEquals(size, output.getSize());
        }
    }

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
        final Instant creationDate = Instant.now();
        final PizzaInfo pizzaInfo = PizzaInfo.builder().name(name).description(description).size(size).build();
        final MenuItem menuItem = MenuItem.builder().id(id).pizzaInfo(pizzaInfo).price(price)
                .creationDate(creationDate).version(version).build();
        List<SelectedItem> selectedItems = singletonList(SelectedItem.builder().id(id).menuItem(menuItem).count(count)
                .createAt(creationDate).build());
        List<Pizza> pizzas = singletonList(Pizza.builder().id(id).name(name).size(size).build());
        final Order order = new Order(id, selectedItems);
        final Ticket ticket = new Ticket(id, order, creationDate);
        CompletedOrder completedOrder = CompletedOrder.builder().items(pizzas).ticket(ticket).id(id)
                .creationDate(creationDate).build();
        final CompletedOrderDtoCrudOutput dtoCrudOutput = CompletedOrderDtoCrudOutput.builder().id(id).ticketId(id)
                .createdAt(creationDate).build();
        Mockito.when(completedOrderService.save(any(CompletedOrder.class))).thenReturn(completedOrder);
        Mockito.when(completedOrderMapper.outputCrudMapping(any(CompletedOrder.class))).thenReturn(dtoCrudOutput);

        //test
        CompletedOrderDtoCrudOutput test = completedOrderManager.save(completedOrder);

        // assert
        Assertions.assertNotNull(test);
        Assertions.assertEquals(id, test.getId());
        Assertions.assertEquals(id, test.getTicketId());
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
        final int size = 32;
        final Instant creationDate = Instant.now();
        final PizzaInfo pizzaInfo = PizzaInfo.builder().name(name).description(description).size(size).build();
        final MenuItem menuItem = MenuItem.builder().id(id).pizzaInfo(pizzaInfo).price(price)
                .creationDate(creationDate).version(version).build();
        List<SelectedItem> selectedItems = singletonList(SelectedItem.builder().id(id).menuItem(menuItem).count(count)
                .createAt(creationDate).build());
        List<Pizza> pizzas = singletonList(Pizza.builder().id(id).name(name).size(size).build());
        final Order order = new Order(id, selectedItems);
        final Ticket ticket = new Ticket(id, order, creationDate);
        CompletedOrder completedOrders = CompletedOrder.builder().items(pizzas).ticket(ticket).id(id)
                .creationDate(creationDate).build();
        final CompletedOrderDtoCrudOutput crudOutput = CompletedOrderDtoCrudOutput.builder().id(id).ticketId(id)
                .createdAt(creationDate).build();
        Mockito.when(completedOrderService.get()).thenReturn(singletonList(completedOrders));
        Mockito.when(completedOrderMapper.outputCrudMapping(any(CompletedOrder.class))).thenReturn(crudOutput);

        //test
        List<CompletedOrderDtoCrudOutput> test = completedOrderManager.get();

        // assert
        Assertions.assertEquals(singletonList(completedOrders).size(), test.size());
        for (CompletedOrderDtoCrudOutput output : test) {
            Assertions.assertNotNull(output);
            Assertions.assertEquals(id, output.getId());
            Assertions.assertEquals(id, output.getTicketId());
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
        final int size = 32;
        final Instant creationDate = Instant.now();
        final PizzaInfo pizzaInfo = PizzaInfo.builder().name(name).description(description).size(size).build();
        final MenuItem menuItem = MenuItem.builder().id(id).pizzaInfo(pizzaInfo).price(price)
                .creationDate(creationDate).version(version).build();
        List<SelectedItem> selectedItems = singletonList(SelectedItem.builder().id(id).menuItem(menuItem).count(count)
                .createAt(creationDate).build());
        List<Pizza> pizzas = singletonList(Pizza.builder().id(id).name(name).size(size).build());
        final Order order = new Order(id, selectedItems);
        final Ticket ticket = new Ticket(id, order, creationDate);
        CompletedOrder completedOrders = CompletedOrder.builder().items(pizzas).ticket(ticket).id(id)
                .creationDate(creationDate).build();
        final CompletedOrderDtoCrudOutput crudOutput = CompletedOrderDtoCrudOutput.builder().id(id).ticketId(id)
                .createdAt(creationDate).build();
        Mockito.when(completedOrderService.get(id)).thenReturn(completedOrders);
        Mockito.when(completedOrderMapper.outputCrudMapping(any(CompletedOrder.class))).thenReturn(crudOutput);

        //test
        CompletedOrderDtoCrudOutput test = completedOrderManager.get(id);

        // assert
        Assertions.assertNotNull(test);
        Assertions.assertEquals(id, test.getId());
        Assertions.assertEquals(id, test.getTicketId());
        Assertions.assertEquals(creationDate, test.getCreatedAt());
    }

    @Test
    void getCompletedOrderByTicket() {
        // preconditions
        final long id = 1L;
        final int version = 1;
        final int count = 10;
        final double price = 18.0;
        final String name = "ITALIANO PIZZA";
        final String description = "Mozzarella cheese, basilica, ham";
        final int size = 32;
        final Instant creationDate = Instant.now();
        final PizzaInfo pizzaInfo = PizzaInfo.builder().name(name).description(description).size(size).build();
        final MenuItem menuItem = MenuItem.builder().id(id).pizzaInfo(pizzaInfo).price(price)
                .creationDate(creationDate).version(version).build();
        List<SelectedItem> selectedItems = singletonList(SelectedItem.builder().id(id).menuItem(menuItem).count(count)
                .createAt(creationDate).build());
        List<Pizza> pizzas = singletonList(Pizza.builder().id(id).name(name).size(size).build());
        final Order order = new Order(id, selectedItems);
        final Ticket ticket = new Ticket(id, order, creationDate);
        CompletedOrder completedOrder = CompletedOrder.builder().items(pizzas).ticket(ticket).id(id)
                .creationDate(creationDate).build();
        List<PizzaDtoOutput> pizzaDtoOutputs = singletonList(PizzaDtoOutput.builder().id(id)
                .name(name).size(size).build());
        final CompletedOrderDtoOutput dtoOutput = CompletedOrderDtoOutput.builder().ticketId(id)
                .items(pizzaDtoOutputs).id(id).createdAt(creationDate).build();
        Mockito.when(completedOrderService.findCompletedOrderByTicketId(id)).thenReturn(completedOrder);
        Mockito.when(completedOrderMapper.outputMapping(any(CompletedOrder.class))).thenReturn(dtoOutput);

        //test
        CompletedOrderDtoOutput test = completedOrderManager.getCompletedOrderByTicket(id);

        // assert
        Assertions.assertNotNull(test);
        Assertions.assertNotNull(test.getItems());
        Assertions.assertEquals(id, test.getId());
        Assertions.assertEquals(creationDate, test.getCreatedAt());
        Assertions.assertEquals(id, test.getTicketId());
        for (PizzaDtoOutput output : test.getItems()) {
            Assertions.assertEquals(id, output.getId());
            Assertions.assertEquals(name, output.getName());
            Assertions.assertEquals(size, output.getSize());
        }
    }
}