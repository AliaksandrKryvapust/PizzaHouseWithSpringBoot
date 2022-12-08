package groupId.artifactId.service;

import groupId.artifactId.dao.MenuDao;
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
class MenuServiceTest {
    @InjectMocks
    private MenuService menuService;
    @Mock
    private MenuDao menuDao;

    @Test
    void get() {
        // preconditions
        final String name = "Optional Menu";
        final boolean enable = false;
        final long id = 1L;
        final int version = 1;
        final Instant creationDate = Instant.now();
        List<IMenu> menus = singletonList(Menu.builder().id(id).creationDate(creationDate).version(version).name(name)
                .enable(enable).build());
        Mockito.when(menuDao.get()).thenReturn(menus);

        //test
        List<IMenu> test = menuService.get();

        // assert
        Assertions.assertEquals(menus.size(), test.size());
        for (IMenu output : test) {
            Assertions.assertNotNull(output);
            Assertions.assertEquals(id, output.getId());
            Assertions.assertEquals(name, output.getName());
            Assertions.assertEquals(enable, output.getEnable());
            Assertions.assertEquals(version, output.getVersion());
            Assertions.assertEquals(creationDate, output.getCreationDate());
        }
    }

    @Test
    void testGet() {
        // preconditions
        final String name = "Optional Menu";
        final boolean enable = false;
        final long id = 1L;
        final int version = 1;
        final Instant creationDate = Instant.now();
        final IMenu menu = Menu.builder().id(id).creationDate(creationDate).version(version).name(name)
                .enable(enable).build();
        Mockito.when(menuDao.get(id)).thenReturn(menu);

        //test
        IMenu test = menuService.get(id);

        // assert
        Assertions.assertNotNull(test);
        Assertions.assertEquals(id, test.getId());
        Assertions.assertEquals(name, test.getName());
        Assertions.assertEquals(enable, test.getEnable());
        Assertions.assertEquals(version, test.getVersion());
        Assertions.assertEquals(creationDate, test.getCreationDate());
    }

    @Test
    void update() {
        // preconditions
        final String name = "Optional Menu";
        final boolean enable = false;
        final long id = 1L;
        final Long inputId = 1L;
        final Integer inputVersion = 1;
        final int version = 1;
        final Instant creationDate = Instant.now();
        final Menu menu = Menu.builder().name(name).enable(enable).build();
        final Menu menuOutput = Menu.builder().id(id).creationDate(creationDate).version(version).name(name)
                .enable(enable).build();
        Mockito.when(menuDao.update(any(IMenu.class), any(Long.class), any(Integer.class)))
                .thenReturn(menuOutput);

        //test
        IMenu test = menuService.update(menu, inputId, inputVersion);

        // assert
        Assertions.assertNotNull(test);
        Assertions.assertEquals(id, test.getId());
        Assertions.assertEquals(name, test.getName());
        Assertions.assertEquals(enable, test.getEnable());
        Assertions.assertEquals(version, test.getVersion());
        Assertions.assertEquals(creationDate, test.getCreationDate());
    }


    @Test
    void updateItem() {
        // preconditions
        final String name = "Optional Menu";
        final boolean enable = false;
        final String pizzaName = "ITALIANO PIZZA";
        final long id = 1L;
        final String description = "Mozzarella cheese, basilica, ham";
        final int size = 32;
        final double price = 18.0;
        final int version = 1;
        final Instant creationDate = Instant.now();
        final PizzaInfo pizzaInfo = PizzaInfo.builder().name(pizzaName).description(description).size(size).build();
        final List<IMenuItem> items = singletonList(MenuItem.builder().id(id).pizzaInfo(pizzaInfo).price(price)
                .creationDate(creationDate).version(version).build());
        final IMenu menu = Menu.builder().id(id).creationDate(creationDate).version(version).name(name)
                .enable(enable).items(items).build();
        final IMenuItem item = MenuItem.builder().pizzaInfo(pizzaInfo).price(price).build();
        Mockito.when(menuDao.updateItems(any(IMenu.class), any(IMenuItem.class)))
                .thenReturn(menu);

        //test
        IMenu test = menuService.updateItem(menu, item);

        // assert
        Assertions.assertNotNull(test);
        Assertions.assertNotNull(test.getItems());
        Assertions.assertEquals(id, test.getId());
        Assertions.assertEquals(name, test.getName());
        Assertions.assertEquals(enable, test.getEnable());
        Assertions.assertEquals(version, test.getVersion());
        Assertions.assertEquals(creationDate, test.getCreationDate());
        for (IMenuItem output : test.getItems()) {
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
    void delete() {
        final Long inputId = 1L;
        final Boolean delete = false;
        ArgumentCaptor<Long> valueId = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Boolean> valueDelete = ArgumentCaptor.forClass(Boolean.class);

        //test
        menuService.delete(inputId, delete);
        Mockito.verify(menuDao, times(1)).delete(valueId.capture(), valueDelete.capture());

        // assert
        Assertions.assertEquals(inputId, valueId.getValue());
        Assertions.assertEquals(delete, valueDelete.getValue());
    }

    @Test
    void save() {
        // preconditions
        final String name = "Optional Menu";
        final boolean enable = false;
        final long id = 1L;
        final int version = 1;
        final Instant creationDate = Instant.now();
        final Menu menu = Menu.builder().name(name).enable(enable).build();
        final Menu menuOutput = Menu.builder().id(id).creationDate(creationDate).version(version).name(name)
                .enable(enable).build();
        Mockito.when(menuDao.save(any(IMenu.class))).thenReturn(menuOutput);

        //test
        IMenu test = menuService.save(menu);

        // assert
        Assertions.assertNotNull(test);
        Assertions.assertEquals(id, test.getId());
        Assertions.assertEquals(name, test.getName());
        Assertions.assertEquals(enable, test.getEnable());
        Assertions.assertEquals(version, test.getVersion());
        Assertions.assertEquals(creationDate, test.getCreationDate());
    }
}