package groupId.artifactId.core.mapper;

import groupId.artifactId.core.dto.input.SelectedItemDtoInput;
import groupId.artifactId.core.dto.output.SelectedItemDtoOutput;
import groupId.artifactId.dao.entity.MenuItem;
import groupId.artifactId.dao.entity.PizzaInfo;
import groupId.artifactId.dao.entity.SelectedItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static java.util.Collections.singletonList;

@ExtendWith(MockitoExtension.class)
class SelectedItemMapperTest {
    @InjectMocks
    private SelectedItemMapper selectedItemMapper;

    @Test
    void inputMapping() {
        // preconditions
        final long id = 1L;
        final int count = 5;
        final double price = 18.0;
        final String name = "ITALIANO PIZZA";
        final String description = "Mozzarella cheese, basilica, ham";
        final int size = 32;
        final int version = 1;
        final Instant creationDate = Instant.now();
        final SelectedItemDtoInput dtoInput = SelectedItemDtoInput.builder().menuItemId(id).count(count).build();
        final PizzaInfo pizzaInfo = PizzaInfo.builder().name(name).description(description).size(size).build();
        final MenuItem menuItem = MenuItem.builder().id(id).pizzaInfo(pizzaInfo).price(price)
                .creationDate(creationDate).version(version).build();

        //test
        SelectedItem test = selectedItemMapper.inputMapping(dtoInput, singletonList(menuItem));

        // assert
        Assertions.assertNotNull(test);
        Assertions.assertNotNull(test.getMenuItem());
        Assertions.assertNotNull(test.getMenuItem().getPizzaInfo());
        Assertions.assertEquals(count, test.getCount());
        Assertions.assertEquals(id, test.getMenuItem().getId());
        Assertions.assertEquals(price, test.getMenuItem().getPrice());
        Assertions.assertEquals(creationDate, test.getMenuItem().getCreationDate());
        Assertions.assertEquals(version, test.getMenuItem().getVersion());
        Assertions.assertEquals(name, test.getMenuItem().getPizzaInfo().getName());
        Assertions.assertEquals(description, test.getMenuItem().getPizzaInfo().getDescription());
        Assertions.assertEquals(size, test.getMenuItem().getPizzaInfo().getSize());
    }

    @Test
    void outputMapping() {
        // preconditions
        final long id = 1L;
        final int count = 10;
        final Instant creationDate = Instant.now();
        final SelectedItem selectedItem = SelectedItem.builder().id(id).menuItemId(id).count(count)
                .createAt(creationDate).build();

        //test
        SelectedItemDtoOutput test = selectedItemMapper.outputMapping(selectedItem);

        // assert
        Assertions.assertNotNull(test);
        Assertions.assertEquals(id, test.getId());
        Assertions.assertEquals(count, test.getCount());
        Assertions.assertEquals(creationDate, test.getCreatedAt());
        Assertions.assertEquals(id, test.getMenuItemId());
    }
}