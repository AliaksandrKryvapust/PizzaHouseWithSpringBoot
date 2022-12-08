package groupId.artifactId.service;

import groupId.artifactId.dao.CompletedOrderDao;
import groupId.artifactId.dao.entity.*;
import groupId.artifactId.dao.entity.api.ICompletedOrder;
import groupId.artifactId.dao.entity.api.IPizza;
import groupId.artifactId.dao.entity.api.ISelectedItem;
import groupId.artifactId.dao.entity.api.ITicket;
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
class CompletedOrderServiceTest {
    @InjectMocks
    private CompletedOrderService completedOrderService;
    @Mock
    private CompletedOrderDao completedOrderDao;

    @Test
    void create() {
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
        List<ISelectedItem> selectedItems = singletonList(SelectedItem.builder().id(id).menuItem(menuItem).count(count)
                .createAt(creationDate).build());
        List<IPizza> pizzas = singletonList(Pizza.builder().id(id).name(name).size(size).build());
        final Order order = new Order(id, selectedItems);
        final ITicket ticket = new Ticket(id, order, creationDate);
        ICompletedOrder completedOrder = CompletedOrder.builder().items(pizzas).ticket(ticket).id(id)
                .creationDate(creationDate).build();
        Mockito.when(completedOrderDao.save(any(ICompletedOrder.class))).thenReturn(completedOrder);

        //test
        ICompletedOrder test = completedOrderService.create(completedOrder);

        // assert
        Assertions.assertNotNull(test);
        Assertions.assertEquals(id, test.getId());
        Assertions.assertEquals(creationDate, test.getCreationDate());
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
        List<ISelectedItem> selectedItems = singletonList(SelectedItem.builder().id(id).menuItem(menuItem).count(count)
                .createAt(creationDate).build());
        List<IPizza> pizzas = singletonList(Pizza.builder().id(id).name(name).size(size).build());
        final Order order = new Order(id, selectedItems);
        final ITicket ticket = new Ticket(id, order, creationDate);
        ICompletedOrder completedOrder = CompletedOrder.builder().items(pizzas).ticket(ticket).id(id)
                .creationDate(creationDate).build();
        Mockito.when(completedOrderDao.save(any(ICompletedOrder.class))).thenReturn(completedOrder);

        //test
        ICompletedOrder test = completedOrderService.save(completedOrder);

        // assert
        Assertions.assertNotNull(test);
        Assertions.assertEquals(id, test.getId());
        Assertions.assertEquals(creationDate, test.getCreationDate());
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
        List<ISelectedItem> selectedItems = singletonList(SelectedItem.builder().id(id).menuItem(menuItem).count(count)
                .createAt(creationDate).build());
        List<IPizza> pizzas = singletonList(Pizza.builder().id(id).name(name).size(size).build());
        final Order order = new Order(id, selectedItems);
        final ITicket ticket = new Ticket(id, order, creationDate);
        ICompletedOrder completedOrders = CompletedOrder.builder().items(pizzas).ticket(ticket).id(id)
                .creationDate(creationDate).build();
        Mockito.when(completedOrderDao.get()).thenReturn(singletonList(completedOrders));

        //test
        List<ICompletedOrder> test = completedOrderService.get();

        // assert
        Assertions.assertEquals(singletonList(completedOrders).size(), test.size());
        for (ICompletedOrder output : test) {
            Assertions.assertNotNull(output);
            Assertions.assertEquals(id, output.getId());
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
        final int size = 32;
        final Instant creationDate = Instant.now();
        final PizzaInfo pizzaInfo = PizzaInfo.builder().name(name).description(description).size(size).build();
        final MenuItem menuItem = MenuItem.builder().id(id).pizzaInfo(pizzaInfo).price(price)
                .creationDate(creationDate).version(version).build();
        List<ISelectedItem> selectedItems = singletonList(SelectedItem.builder().id(id).menuItem(menuItem).count(count)
                .createAt(creationDate).build());
        List<IPizza> pizzas = singletonList(Pizza.builder().id(id).name(name).size(size).build());
        final Order order = new Order(id, selectedItems);
        final ITicket ticket = new Ticket(id, order, creationDate);
        ICompletedOrder completedOrders = CompletedOrder.builder().items(pizzas).ticket(ticket).id(id)
                .creationDate(creationDate).build();
        Mockito.when(completedOrderDao.get(id)).thenReturn(completedOrders);

        //test
        ICompletedOrder test = completedOrderService.get(id);

        // assert
        Assertions.assertNotNull(test);
        Assertions.assertEquals(id, test.getId());
        Assertions.assertEquals(creationDate, test.getCreationDate());
    }
}