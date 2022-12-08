package groupId.artifactId.manager;

import groupId.artifactId.core.dto.input.MenuDtoInput;
import groupId.artifactId.core.dto.output.MenuDtoOutput;
import groupId.artifactId.core.dto.output.MenuItemDtoOutput;
import groupId.artifactId.core.dto.output.PizzaInfoDtoOutput;
import groupId.artifactId.core.dto.output.crud.MenuDtoCrudOutput;
import groupId.artifactId.core.mapper.MenuMapper;
import groupId.artifactId.dao.entity.Menu;
import groupId.artifactId.dao.entity.MenuItem;
import groupId.artifactId.dao.entity.PizzaInfo;
import groupId.artifactId.dao.entity.api.IMenu;
import groupId.artifactId.dao.entity.api.IMenuItem;
import groupId.artifactId.service.MenuService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.Instant;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

class MenuManagerTest {
    @InjectMocks
    private MenuManager menuManager;
    @Mock
    private MenuService menuService;
    @Mock
    private MenuMapper menuMapper;

    @Test
    void getAllData() {
        // preconditions
        final String name = "Optional Menu";
        final boolean enable = false;
        final long id = 1L;
        final int version = 1;
        final double price = 18.0;
        final String pizzaName = "ITALIANO PIZZA";
        final String description = "Mozzarella cheese, basilica, ham";
        final int size = 32;
        final Instant creationDate = Instant.now();
        final PizzaInfo pizzaInfo = PizzaInfo.builder().name(name).description(description).size(size).build();
        final List<IMenuItem> items = singletonList(MenuItem.builder().id(id).pizzaInfo(pizzaInfo).price(price)
                .creationDate(creationDate).version(version).build());
        final IMenu menu = Menu.builder().id(id).creationDate(creationDate).version(version).name(name)
                .enable(enable).items(items).build();
        final PizzaInfoDtoOutput pizzaInfoDtoOutput = PizzaInfoDtoOutput.builder().name(pizzaName).description(description)
                .size(size).build();
        final MenuItemDtoOutput menuItemDtoOutput = MenuItemDtoOutput.builder().id(id).price(price)
                .createdAt(creationDate).version(version).pizzaInfo(pizzaInfoDtoOutput).build();
        final MenuDtoOutput dtoOutput = MenuDtoOutput.builder().id(id).createdAt(creationDate).version(version)
                .name(name).enable(enable).items(singletonList(menuItemDtoOutput)).build();
        Mockito.when(menuService.get(id)).thenReturn(menu);
        Mockito.when(menuMapper.outputMapping(any(IMenu.class))).thenReturn(dtoOutput);

        //test
        MenuDtoOutput test = menuManager.getAllData(id);

        // assert
        Assertions.assertNotNull(test);
        Assertions.assertNotNull(test.getItems());
        Assertions.assertEquals(id, test.getId());
        Assertions.assertEquals(name, test.getName());
        Assertions.assertEquals(enable, test.getEnable());
        Assertions.assertEquals(version, test.getVersion());
        Assertions.assertEquals(creationDate, test.getCreatedAt());
        for (MenuItemDtoOutput output : test.getItems()) {
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
    void save() {
        // preconditions
        final String name = "Optional Menu";
        final boolean enable = false;
        final long id = 1L;
        final int version = 1;
        final Instant creationDate = Instant.now();
        final MenuDtoInput menuDtoInput = MenuDtoInput.builder().name(name).enable(enable).build();
        final Menu menu = Menu.builder().name(name).enable(enable).build();
        final Menu menuOutput = Menu.builder().id(id).creationDate(creationDate).version(version).name(name)
                .enable(enable).build();
        final MenuDtoCrudOutput crudOutput = MenuDtoCrudOutput.builder().id(id).createdAt(creationDate).version(version)
                .name(name).enable(enable).build();
        Mockito.when(menuMapper.inputMapping(any(MenuDtoInput.class))).thenReturn(menu);
        Mockito.when(menuService.save(any(IMenu.class))).thenReturn(menuOutput);
        Mockito.when(menuMapper.outputCrudMapping(any(IMenu.class))).thenReturn(crudOutput);

        //test
        MenuDtoCrudOutput test = menuManager.save(menuDtoInput);

        // assert
        Assertions.assertNotNull(test);
        Assertions.assertEquals(id, test.getId());
        Assertions.assertEquals(name, test.getName());
        Assertions.assertEquals(enable, test.getEnable());
        Assertions.assertEquals(version, test.getVersion());
        Assertions.assertEquals(creationDate, test.getCreatedAt());
    }

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
        final MenuDtoCrudOutput crudOutput = MenuDtoCrudOutput.builder().id(id).createdAt(creationDate).version(version)
                .name(name).enable(enable).build();
        Mockito.when(menuService.get()).thenReturn(menus);
        Mockito.when(menuMapper.outputCrudMapping(any(IMenu.class))).thenReturn(crudOutput);

        //test
        List<MenuDtoCrudOutput> test = menuManager.get();

        // assert
        Assertions.assertEquals(menus.size(), test.size());
        for (MenuDtoCrudOutput output : test) {
            Assertions.assertNotNull(output);
            Assertions.assertEquals(id, output.getId());
            Assertions.assertEquals(name, output.getName());
            Assertions.assertEquals(enable, output.getEnable());
            Assertions.assertEquals(version, output.getVersion());
            Assertions.assertEquals(creationDate, output.getCreatedAt());
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
        final MenuDtoCrudOutput crudOutput = MenuDtoCrudOutput.builder().id(id).createdAt(creationDate).version(version)
                .name(name).enable(enable).build();
        Mockito.when(menuService.get(id)).thenReturn(menu);
        Mockito.when(menuMapper.outputCrudMapping(any(IMenu.class))).thenReturn(crudOutput);

        //test
        MenuDtoCrudOutput test = menuManager.get(id);

        // assert
        Assertions.assertNotNull(test);
        Assertions.assertEquals(id, test.getId());
        Assertions.assertEquals(name, test.getName());
        Assertions.assertEquals(enable, test.getEnable());
        Assertions.assertEquals(version, test.getVersion());
        Assertions.assertEquals(creationDate, test.getCreatedAt());
    }

    @Test
    void delete() {
        final Long inputId = 1L;
        final Boolean delete = false;
        ArgumentCaptor<Long> valueId = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Boolean> valueDelete = ArgumentCaptor.forClass(Boolean.class);

        //test
        menuService.delete(inputId, delete);
        Mockito.verify(menuService, times(1)).delete(valueId.capture(), valueDelete.capture());

        // assert
        Assertions.assertEquals(inputId, valueId.getValue());
        Assertions.assertEquals(delete, valueDelete.getValue());
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
        final MenuDtoInput menuDtoInput = MenuDtoInput.builder().name(name).enable(enable).build();
        final Menu menu = Menu.builder().name(name).enable(enable).build();
        final Menu menuOutput = Menu.builder().id(id).creationDate(creationDate).version(version).name(name)
                .enable(enable).build();
        final MenuDtoCrudOutput dtoOutput = MenuDtoCrudOutput.builder().id(id).createdAt(creationDate).version(version)
                .name(name).enable(enable).build();
        Mockito.when(menuMapper.inputMapping(any(MenuDtoInput.class))).thenReturn(menu);
        Mockito.when(menuService.update(any(IMenu.class), any(Long.class), any(Integer.class))).thenReturn(menuOutput);
        Mockito.when(menuMapper.outputCrudMapping(any(IMenu.class))).thenReturn(dtoOutput);

        //test
        MenuDtoCrudOutput test = menuManager.update(menuDtoInput, inputId, inputVersion);

        // assert
        Assertions.assertNotNull(test);
        Assertions.assertEquals(id, test.getId());
        Assertions.assertEquals(name, test.getName());
        Assertions.assertEquals(enable, test.getEnable());
        Assertions.assertEquals(version, test.getVersion());
        Assertions.assertEquals(creationDate, test.getCreatedAt());
    }
}