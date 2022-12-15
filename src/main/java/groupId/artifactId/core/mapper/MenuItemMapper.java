package groupId.artifactId.core.mapper;

import groupId.artifactId.core.dto.input.MenuItemDtoInput;
import groupId.artifactId.core.dto.output.MenuItemDtoOutput;
import groupId.artifactId.core.dto.output.PizzaInfoDtoOutput;
import groupId.artifactId.dao.entity.MenuItem;
import groupId.artifactId.dao.entity.PizzaInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MenuItemMapper {
    private final PizzaInfoMapper pizzaInfoMapper;
    @Autowired
    public MenuItemMapper(PizzaInfoMapper pizzaInfoMapper) {
        this.pizzaInfoMapper = pizzaInfoMapper;
    }

    public MenuItem inputMapping(MenuItemDtoInput menuItemDtoInput) {
        PizzaInfo pizzaInfo = pizzaInfoMapper.inputMapping(menuItemDtoInput.getPizzaInfoDtoInput());
        return MenuItem.builder()
                .price(menuItemDtoInput.getPrice())
                .menuId(menuItemDtoInput.getMenuId())
                .pizzaInfo(pizzaInfo).build();
    }

    public MenuItemDtoOutput outputMapping(MenuItem menuItem) {
        PizzaInfoDtoOutput pizzaInfo = pizzaInfoMapper.outputMapping(menuItem.getPizzaInfo());
        return MenuItemDtoOutput.builder()
                .id(menuItem.getId())
                .menuId(menuItem.getMenuId())
                .price(menuItem.getPrice())
                .createdAt(menuItem.getCreationDate())
                .version(menuItem.getVersion())
                .pizzaInfo(pizzaInfo).build();
    }
}
