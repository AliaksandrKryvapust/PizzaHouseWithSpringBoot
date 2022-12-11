package groupId.artifactId.service;

import groupId.artifactId.core.dto.input.OrderDataDtoInput;
import groupId.artifactId.dao.api.ITicketDao;
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
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @InjectMocks
    private OrderService orderService;
    @Mock
    private ITicketDao ticketDao;

    @Mock
    private OrderDataService orderDataService;

    @Test
    void save() {
        // preconditions
        final long id = 1L;
        final int count = 5;
        final String description = "Order accepted";
        final double price = 18.0;
        final String name = "ITALIANO PIZZA";
        final String pizzaDescription = "Mozzarella cheese, basilica, ham";
        final int size = 32;
        final int version = 1;
        final Instant creationDate = Instant.now();
        final PizzaInfo pizzaInfo = PizzaInfo.builder().name(name).description(pizzaDescription).size(size).build();
        final MenuItem menuItem = MenuItem.builder().id(id).pizzaInfo(pizzaInfo).price(price)
                .creationDate(creationDate).version(version).build();
        List<SelectedItem> selectedItems = singletonList(SelectedItem.builder().id(id).count(count).menuItem(menuItem)
                .createAt(creationDate).build());
        final Order order = new Order(id, selectedItems);
        final Ticket ticketInput = Ticket.builder().order(order).createAt(creationDate).build();
        final Ticket ticket = new Ticket(id, order, creationDate);
        Mockito.when(ticketDao.save(any(Ticket.class))).thenReturn(ticket);
        ArgumentCaptor<OrderDataDtoInput> value = ArgumentCaptor.forClass(OrderDataDtoInput.class);

        //test
        Ticket test = orderService.save(ticketInput);
        Mockito.verify(orderDataService, times(1)).create(value.capture());

        // assert
        Assertions.assertNotNull(test);
        Assertions.assertEquals(id, test.getId());
        Assertions.assertEquals(id, test.getOrder().getId());
        Assertions.assertEquals(id, value.getValue().getTicketId());
        Assertions.assertEquals(description, value.getValue().getDescription());
    }

    @Test
    void get() {
        // preconditions
        final long id = 1L;
        final Instant creationDate = Instant.now();
        List<Ticket> tickets = singletonList(Ticket.builder().id(id).createAt(creationDate).build());
        Mockito.when(ticketDao.findAll()).thenReturn(tickets);

        //test
        List<Ticket> test = orderService.get();

        // assert
        Assertions.assertEquals(tickets.size(), test.size());
        for (Ticket ticket : test) {
            Assertions.assertNotNull(ticket);
            Assertions.assertEquals(id, ticket.getId());
            Assertions.assertEquals(creationDate, ticket.getCreateAt());
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
        final Order order = new Order(id, selectedItems);
        final Ticket ticket = Ticket.builder().id(id).order(order).createAt(creationDate).build();
        Mockito.when(ticketDao.findById(id)).thenReturn(Optional.of(ticket));

        //test
        Ticket test = orderService.get(id);

        // assert
        Assertions.assertNotNull(test);
        Assertions.assertEquals(id, test.getId());
        Assertions.assertEquals(creationDate, test.getCreateAt());
    }
}