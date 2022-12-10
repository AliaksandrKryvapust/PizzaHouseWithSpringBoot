package groupId.artifactId.service;

import groupId.artifactId.dao.api.IMenuDao;
import groupId.artifactId.dao.entity.Menu;
import groupId.artifactId.dao.entity.MenuItem;
import groupId.artifactId.dao.entity.PizzaInfo;
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

import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {
    @InjectMocks
    private MenuService menuService;
    @Mock
    private IMenuDao menuDao;

    @Test
    void get() {
        // preconditions
        final String name = "Optional Menu";
        final boolean enable = false;
        final long id = 1L;
        final int version = 1;
        final Instant creationDate = Instant.now();
        List<Menu> menus = singletonList(Menu.builder().id(id).creationDate(creationDate).version(version).name(name)
                .enable(enable).build());
        Mockito.when(menuDao.findAll()).thenReturn(menus);

        //test
        List<Menu> test = menuService.get();

        // assert
        Assertions.assertEquals(menus.size(), test.size());
        for (Menu output : test) {
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
        final Menu menu = Menu.builder().id(id).creationDate(creationDate).version(version).name(name)
                .enable(enable).build();
        Mockito.when(menuDao.findById(id)).thenReturn(Optional.of(menu));

        //test
        Menu test = menuService.get(id);

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
        Mockito.when(menuDao.findById(id)).thenReturn(Optional.of(menuOutput));
        Mockito.when(menuDao.save(any(Menu.class))).thenReturn(menuOutput);

        //test
        Menu test = menuService.update(menu, inputId, inputVersion);

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
        List<MenuItem> items = new ArrayList<>();
        final MenuItem menuItem = MenuItem.builder().id(id).pizzaInfo(pizzaInfo).price(price)
                .creationDate(creationDate).version(version).build();
        items.add(menuItem);
        final Menu menu = Menu.builder().id(id).creationDate(creationDate).version(version).name(name)
                .enable(enable).items(items).build();
        List<MenuItem> outputs = new ArrayList<>();
        outputs.add(menuItem);
        outputs.add(menuItem);
        final Menu menuOutput = Menu.builder().id(id).creationDate(creationDate).version(version).name(name)
                .enable(enable).items(outputs).build();
        final MenuItem item = MenuItem.builder().pizzaInfo(pizzaInfo).price(price).build();
        Mockito.when(menuDao.save(any(Menu.class))).thenReturn(menuOutput);

        //test
        Menu test = menuService.updateItem(menu, item);

        // assert
        Assertions.assertNotNull(test);
        Assertions.assertNotNull(test.getItems());
        Assertions.assertEquals(id, test.getId());
        Assertions.assertEquals(name, test.getName());
        Assertions.assertEquals(enable, test.getEnable());
        Assertions.assertEquals(version, test.getVersion());
        Assertions.assertEquals(creationDate, test.getCreationDate());
        for (MenuItem output : test.getItems()) {
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
        ArgumentCaptor<Long> valueId = ArgumentCaptor.forClass(Long.class);

        //test
        menuService.delete(inputId);
        Mockito.verify(menuDao, times(1)).deleteById(valueId.capture());

        // assert
        Assertions.assertEquals(inputId, valueId.getValue());
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
        Mockito.when(menuDao.save(any(Menu.class))).thenReturn(menuOutput);

        //test
        Menu test = menuService.save(menu);

        // assert
        Assertions.assertNotNull(test);
        Assertions.assertEquals(id, test.getId());
        Assertions.assertEquals(name, test.getName());
        Assertions.assertEquals(enable, test.getEnable());
        Assertions.assertEquals(version, test.getVersion());
        Assertions.assertEquals(creationDate, test.getCreationDate());
    }
}