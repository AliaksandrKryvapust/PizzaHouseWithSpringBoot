package groupId.artifactId.service;

import groupId.artifactId.dao.MenuItemDao;
import groupId.artifactId.dao.entity.Menu;
import groupId.artifactId.dao.entity.MenuItem;
import groupId.artifactId.dao.entity.PizzaInfo;
import groupId.artifactId.dao.entity.api.IMenu;
import groupId.artifactId.dao.entity.api.IMenuItem;
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

import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class MenuItemServiceTest {
    @InjectMocks
    private MenuItemService menuItemService;
    @Mock
    private MenuItemDao menuItemDao;
    @Mock
    private MenuService menuService;

    @Test
    void saveConditionOne() {
        // preconditions
        final long id = 1L;
        final double price = 20.0;
        final String pizzaName = "ITALIANO PIZZA";
        final String description = "Mozzarella cheese, basilica, ham";
        final int size = 32;
        final int version = 1;
        final Instant creationDate = Instant.now();
        final PizzaInfo pizzaInfo = PizzaInfo.builder().name(pizzaName).description(description).size(size).build();
        final MenuItem menuItem = MenuItem.builder().id(id).price(price).pizzaInfo(pizzaInfo)
                .creationDate(creationDate).version(version).build();
        Mockito.when(menuItemDao.save(any(IMenuItem.class))).thenReturn(menuItem);

        //test
        IMenuItem test = menuItemService.saveInTransaction(menuItem, null);

        // assert
        Assertions.assertNotNull(test);
        Assertions.assertNotNull(test.getPizzaInfo());
        Assertions.assertEquals(id, test.getId());
        Assertions.assertEquals(price, test.getPrice());
        Assertions.assertEquals(version, test.getVersion());
        Assertions.assertEquals(creationDate, test.getCreationDate());
        Assertions.assertEquals(pizzaName, test.getPizzaInfo().getName());
        Assertions.assertEquals(description, test.getPizzaInfo().getDescription());
        Assertions.assertEquals(size, test.getPizzaInfo().getSize());
    }

    @Test
    void saveConditionTwo() {
        // preconditions
        final long id = 1L;
        final String name = "Optional Menu";
        final boolean enable = false;
        final double price = 20.0;
        final String pizzaName = "ITALIANO PIZZA";
        final String description = "Mozzarella cheese, basilica, ham";
        final int size = 32;
        final int version = 1;
        final Instant creationDate = Instant.now();
        final PizzaInfo pizzaInfo = PizzaInfo.builder().name(pizzaName).description(description).size(size).build();
        final List<IMenuItem> items = singletonList(MenuItem.builder().id(id).pizzaInfo(pizzaInfo).price(price)
                .creationDate(creationDate).version(version).build());
        final IMenu menu = Menu.builder().id(id).creationDate(creationDate).version(version).name(name)
                .enable(enable).items(items).build();
        final MenuItem menuItem = MenuItem.builder().id(id).price(price).pizzaInfo(pizzaInfo)
                .creationDate(creationDate).version(version).build();
        Mockito.when(menuItemDao.save(any(IMenuItem.class))).thenReturn(menuItem);
        Mockito.when(menuService.get(any(Long.class))).thenReturn(menu);
        Mockito.when(menuService.updateItem(any(Menu.class), any(MenuItem.class))).thenReturn(menu);

        //test
        IMenuItem test = menuItemService.saveInTransaction(menuItem, id);

        // assert
        Assertions.assertNotNull(test);
        Assertions.assertNotNull(test.getPizzaInfo());
        Assertions.assertEquals(id, test.getId());
        Assertions.assertEquals(price, test.getPrice());
        Assertions.assertEquals(version, test.getVersion());
        Assertions.assertEquals(creationDate, test.getCreationDate());
        Assertions.assertEquals(pizzaName, test.getPizzaInfo().getName());
        Assertions.assertEquals(description, test.getPizzaInfo().getDescription());
        Assertions.assertEquals(size, test.getPizzaInfo().getSize());
    }

    @Test
    void get() {
        // preconditions
        final long id = 1L;
        final double price = 20.0;
        final String pizzaName = "ITALIANO PIZZA";
        final String description = "Mozzarella cheese, basilica, ham";
        final int size = 32;
        final int version = 1;
        final Instant creationDate = Instant.now();
        final PizzaInfo pizzaInfo = PizzaInfo.builder().name(pizzaName).description(description).size(size).build();
        List<IMenuItem> menuItems = singletonList(MenuItem.builder().id(id).price(price)
                .pizzaInfo(pizzaInfo).creationDate(creationDate).version(version).build());
        Mockito.when(menuItemDao.get()).thenReturn(menuItems);

        //test
        List<IMenuItem> test = menuItemService.get();

        // assert
        Assertions.assertEquals(menuItems.size(), test.size());
        for (IMenuItem output : test) {
            Assertions.assertNotNull(output);
            Assertions.assertNotNull(output.getPizzaInfo());
            Assertions.assertEquals(id, output.getId());
            Assertions.assertEquals(price, output.getPrice());
            Assertions.assertEquals(version, output.getVersion());
            Assertions.assertEquals(creationDate, output.getCreationDate());
            Assertions.assertEquals(pizzaName, output.getPizzaInfo().getName());
            Assertions.assertEquals(description, output.getPizzaInfo().getDescription());
            Assertions.assertEquals(size, output.getPizzaInfo().getSize());
        }
    }

    @Test
    void testGet() {
        // preconditions
        final long id = 1L;
        final double price = 20.0;
        final String pizzaName = "ITALIANO PIZZA";
        final String description = "Mozzarella cheese, basilica, ham";
        final int size = 32;
        final int version = 1;
        final Instant creationDate = Instant.now();
        final PizzaInfo pizzaInfo = PizzaInfo.builder().name(pizzaName).description(description).size(size).build();
        final IMenuItem menuItem = MenuItem.builder().id(id).price(price).pizzaInfo(pizzaInfo)
                .creationDate(creationDate).version(version).build();
        Mockito.when(menuItemDao.get(id)).thenReturn(menuItem);

        //test
        IMenuItem test = menuItemService.get(id);

        // assert
        Assertions.assertNotNull(test);
        Assertions.assertNotNull(test.getPizzaInfo());
        Assertions.assertEquals(id, test.getId());
        Assertions.assertEquals(price, test.getPrice());
        Assertions.assertEquals(version, test.getVersion());
        Assertions.assertEquals(creationDate, test.getCreationDate());
        Assertions.assertEquals(pizzaName, test.getPizzaInfo().getName());
        Assertions.assertEquals(description, test.getPizzaInfo().getDescription());
        Assertions.assertEquals(size, test.getPizzaInfo().getSize());
    }

    @Test
    void updateConditionOne() {
        // preconditions
        final long id = 1L;
        final double price = 20.0;
        final String pizzaName = "ITALIANO PIZZA";
        final String description = "Mozzarella cheese, basilica, ham";
        final int size = 32;
        final int version = 1;
        final Long inputId = 1L;
        final Integer inputVersion = 1;
        final Instant creationDate = Instant.now();
        final PizzaInfo pizzaInfo = PizzaInfo.builder().name(pizzaName).description(description).size(size).build();
        final MenuItem menuItem = MenuItem.builder().id(id).price(price).pizzaInfo(pizzaInfo)
                .creationDate(creationDate).version(version).build();
        final MenuItem menuItemInput = MenuItem.builder().price(price).pizzaInfo(pizzaInfo).build();
        Mockito.when(menuItemDao.update(any(IMenuItem.class), any(Long.class), any(Integer.class))).thenReturn(menuItem);

        //test
        IMenuItem test = menuItemService.updateInTransaction(menuItemInput, null, inputId, inputVersion);

        // assert
        Assertions.assertNotNull(test);
        Assertions.assertNotNull(test.getPizzaInfo());
        Assertions.assertEquals(id, test.getId());
        Assertions.assertEquals(price, test.getPrice());
        Assertions.assertEquals(version, test.getVersion());
        Assertions.assertEquals(creationDate, test.getCreationDate());
        Assertions.assertEquals(pizzaName, test.getPizzaInfo().getName());
        Assertions.assertEquals(description, test.getPizzaInfo().getDescription());
        Assertions.assertEquals(size, test.getPizzaInfo().getSize());
    }

    @Test
    void updateConditionTwo() {
        // preconditions
        final long id = 1L;
        final double price = 20.0;
        final String pizzaName = "ITALIANO PIZZA";
        final String description = "Mozzarella cheese, basilica, ham";
        final int size = 32;
        final String name = "Optional Menu";
        final boolean enable = false;
        final int version = 1;
        final Long inputId = 1L;
        final Integer inputVersion = 1;
        final Instant creationDate = Instant.now();
        final PizzaInfo pizzaInfo = PizzaInfo.builder().name(pizzaName).description(description).size(size).build();
        final List<IMenuItem> items = singletonList(MenuItem.builder().id(id).pizzaInfo(pizzaInfo).price(price)
                .creationDate(creationDate).version(version).build());
        final IMenu menu = Menu.builder().id(id).creationDate(creationDate).version(version).name(name)
                .enable(enable).items(items).build();
        final MenuItem menuItemInput = MenuItem.builder().price(price).pizzaInfo(pizzaInfo).build();
        final MenuItem menuItem = MenuItem.builder().id(id).price(price).pizzaInfo(pizzaInfo)
                .creationDate(creationDate).version(version).build();
        Mockito.when(menuItemDao.update(any(IMenuItem.class), any(Long.class), any(Integer.class))).thenReturn(menuItem);
        Mockito.when(menuService.get(any(Long.class))).thenReturn(menu);
        Mockito.when(menuService.updateItem(any(Menu.class), any(MenuItem.class))).thenReturn(menu);

        //test
        IMenuItem test = menuItemService.updateInTransaction(menuItemInput, id, inputId, inputVersion);

        // assert
        Assertions.assertNotNull(test);
        Assertions.assertNotNull(test.getPizzaInfo());
        Assertions.assertEquals(id, test.getId());
        Assertions.assertEquals(price, test.getPrice());
        Assertions.assertEquals(version, test.getVersion());
        Assertions.assertEquals(creationDate, test.getCreationDate());
        Assertions.assertEquals(pizzaName, test.getPizzaInfo().getName());
        Assertions.assertEquals(description, test.getPizzaInfo().getDescription());
        Assertions.assertEquals(size, test.getPizzaInfo().getSize());
    }

    @Test
    void delete() {
        // preconditions
        final Long inputId = 1L;
        final Boolean delete = false;
        ArgumentCaptor<Long> valueId = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Boolean> valueDelete = ArgumentCaptor.forClass(Boolean.class);

        //test
        menuItemService.delete(inputId, delete);
        Mockito.verify(menuItemDao, times(1)).delete(valueId.capture(), valueDelete.capture());

        // assert
        Assertions.assertEquals(inputId, valueId.getValue());
        Assertions.assertEquals(delete, valueDelete.getValue());
    }

}