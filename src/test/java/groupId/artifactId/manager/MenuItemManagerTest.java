package groupId.artifactId.manager;

import groupId.artifactId.core.dto.input.MenuItemDtoInput;
import groupId.artifactId.core.dto.input.PizzaInfoDtoInput;
import groupId.artifactId.core.dto.output.MenuItemDtoOutput;
import groupId.artifactId.core.dto.output.PizzaInfoDtoOutput;
import groupId.artifactId.core.mapper.MenuItemMapper;
import groupId.artifactId.dao.entity.MenuItem;
import groupId.artifactId.dao.entity.PizzaInfo;
import groupId.artifactId.dao.entity.api.IMenuItem;
import groupId.artifactId.service.MenuItemService;
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
class MenuItemManagerTest {

    @InjectMocks
    private MenuItemManager menuItemManager;
    @Mock
    private MenuItemMapper menuItemMapper;
    @Mock
    private MenuItemService menuItemService;

    @Test
    void save() {
        // preconditions
        final long id = 1L;
        final double price = 20.0;
        final String pizzaName = "ITALIANO PIZZA";
        final String description = "Mozzarella cheese, basilica, ham";
        final int size = 32;
        final int version = 1;
        final Instant creationDate = Instant.now();
        final PizzaInfo pizzaInfo = PizzaInfo.builder().name(pizzaName).description(description).size(size).build();
        final IMenuItem menuItemInput = MenuItem.builder().price(price).build();
        final PizzaInfoDtoInput pizzaInfoDtoInput = PizzaInfoDtoInput.builder().name(pizzaName).description(description)
                .size(size).build();
        final MenuItemDtoInput menuDtoInput = MenuItemDtoInput.builder().price(price)
                .pizzaInfoDtoInput(pizzaInfoDtoInput).menuId(id).build();
        final MenuItem menuItemOutput = MenuItem.builder().id(id).price(price).pizzaInfo(pizzaInfo)
                .creationDate(creationDate).version(version).build();
        final PizzaInfoDtoOutput pizzaInfoDtoOutput = PizzaInfoDtoOutput.builder().name(pizzaName).description(description)
                .size(size).build();
        final MenuItemDtoOutput dtoOutput = MenuItemDtoOutput.builder().id(id).price(price)
                .createdAt(creationDate).version(version).pizzaInfo(pizzaInfoDtoOutput).build();
        Mockito.when(menuItemMapper.inputMapping(any(MenuItemDtoInput.class))).thenReturn(menuItemInput);
        Mockito.when(menuItemService.saveInTransaction(any(IMenuItem.class), any(Long.class))).thenReturn(menuItemOutput);
        Mockito.when(menuItemMapper.outputMapping(any(IMenuItem.class))).thenReturn(dtoOutput);

        //test
        MenuItemDtoOutput test = menuItemManager.save(menuDtoInput);

        // assert
        Assertions.assertNotNull(test);
        Assertions.assertNotNull(test.getPizzaInfo());
        Assertions.assertEquals(id, test.getId());
        Assertions.assertEquals(price, test.getPrice());
        Assertions.assertEquals(version, test.getVersion());
        Assertions.assertEquals(creationDate, test.getCreatedAt());
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
        List<IMenuItem> menuItems = singletonList(MenuItem.builder().id(id).price(price)
                .creationDate(creationDate).version(version).build());
        final PizzaInfoDtoOutput pizzaInfoDtoOutput = PizzaInfoDtoOutput.builder().name(pizzaName).description(description)
                .size(size).build();
        final MenuItemDtoOutput dtoOutput = MenuItemDtoOutput.builder().id(id).price(price)
                .createdAt(creationDate).version(version).pizzaInfo(pizzaInfoDtoOutput).build();
        Mockito.when(menuItemService.get()).thenReturn(menuItems);
        Mockito.when(menuItemMapper.outputMapping(any(IMenuItem.class))).thenReturn(dtoOutput);

        //test
        List<MenuItemDtoOutput> test = menuItemManager.get();

        // assert
        Assertions.assertEquals(menuItems.size(), test.size());
        for (MenuItemDtoOutput output : test) {
            Assertions.assertNotNull(output);
            Assertions.assertNotNull(output.getPizzaInfo());
            Assertions.assertEquals(id, output.getId());
            Assertions.assertEquals(price, output.getPrice());
            Assertions.assertEquals(version, output.getVersion());
            Assertions.assertEquals(creationDate, output.getCreatedAt());
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
        final PizzaInfoDtoOutput pizzaInfoDtoOutput = PizzaInfoDtoOutput.builder().name(pizzaName).description(description)
                .size(size).build();
        final MenuItemDtoOutput dtoOutput = MenuItemDtoOutput.builder().id(id).price(price)
                .createdAt(creationDate).version(version).pizzaInfo(pizzaInfoDtoOutput).build();
        Mockito.when(menuItemService.get(id)).thenReturn(menuItem);
        Mockito.when(menuItemMapper.outputMapping(any(IMenuItem.class))).thenReturn(dtoOutput);

        //test
        MenuItemDtoOutput test = menuItemManager.get(id);

        // assert
        Assertions.assertNotNull(test);
        Assertions.assertNotNull(test.getPizzaInfo());
        Assertions.assertEquals(id, test.getId());
        Assertions.assertEquals(price, test.getPrice());
        Assertions.assertEquals(version, test.getVersion());
        Assertions.assertEquals(creationDate, test.getCreatedAt());
        Assertions.assertEquals(pizzaName, test.getPizzaInfo().getName());
        Assertions.assertEquals(description, test.getPizzaInfo().getDescription());
        Assertions.assertEquals(size, test.getPizzaInfo().getSize());
    }

    @Test
    void delete() {
        // preconditions
        final Long id = 1L;
        final Boolean delete = false;
        ArgumentCaptor<Long> valueId = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Boolean> valueDelete = ArgumentCaptor.forClass(Boolean.class);

        //test
        menuItemManager.delete(id, delete);
        Mockito.verify(menuItemService, times(1)).delete(valueId.capture(), valueDelete.capture());

        // assert
        Assertions.assertEquals(id, valueId.getValue());
        Assertions.assertEquals(delete, valueDelete.getValue());
    }

    @Test
    void update() {
        // preconditions
        final long id = 1L;
        final double price = 20.0;
        final String pizzaName = "ITALIANO PIZZA";
        final String description = "Mozzarella cheese, basilica, ham";
        final int size = 32;
        final int version = 1;
        final Instant creationDate = Instant.now();
        final PizzaInfo pizzaInfo = PizzaInfo.builder().name(pizzaName).description(description).size(size).build();
        final PizzaInfoDtoInput pizzaInfoDtoInput = PizzaInfoDtoInput.builder().name(pizzaName).description(description)
                .size(size).build();
        final MenuItemDtoInput menuDtoInput = MenuItemDtoInput.builder().price(price)
                .pizzaInfoDtoInput(pizzaInfoDtoInput).menuId(id).build();
        final MenuItem menuItem = MenuItem.builder().id(id).price(price).pizzaInfo(pizzaInfo)
                .creationDate(creationDate).version(version).build();
        final MenuItem menuItemInput = MenuItem.builder().price(price).pizzaInfo(pizzaInfo).build();
        final PizzaInfoDtoOutput pizzaInfoDtoOutput = PizzaInfoDtoOutput.builder().name(pizzaName).description(description)
                .size(size).build();
        final MenuItemDtoOutput dtoOutput = MenuItemDtoOutput.builder().id(id).price(price)
                .createdAt(creationDate).version(version).pizzaInfo(pizzaInfoDtoOutput).build();
        Mockito.when(menuItemMapper.inputMapping(any(MenuItemDtoInput.class))).thenReturn(menuItemInput);
        Mockito.when(menuItemService.updateInTransaction(any(IMenuItem.class), any(Long.class), any(Long.class), any(Integer.class)))
                .thenReturn(menuItem);
        Mockito.when(menuItemMapper.outputMapping(any(IMenuItem.class))).thenReturn(dtoOutput);

        //test
        MenuItemDtoOutput test = menuItemManager.update(menuDtoInput, id, version);

        // assert
        Assertions.assertNotNull(test);
        Assertions.assertNotNull(test.getPizzaInfo());
        Assertions.assertEquals(id, test.getId());
        Assertions.assertEquals(price, test.getPrice());
        Assertions.assertEquals(version, test.getVersion());
        Assertions.assertEquals(creationDate, test.getCreatedAt());
        Assertions.assertEquals(pizzaName, test.getPizzaInfo().getName());
        Assertions.assertEquals(description, test.getPizzaInfo().getDescription());
        Assertions.assertEquals(size, test.getPizzaInfo().getSize());
    }
}