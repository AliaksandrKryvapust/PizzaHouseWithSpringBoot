package groupId.artifactId.service;

import groupId.artifactId.core.dto.MenuDto;
import groupId.artifactId.core.dto.MenuItemDto;
import groupId.artifactId.service.api.IMenuValidator;

import java.util.List;

public class MenuValidator implements IMenuValidator {
    private static MenuValidator firstInstance = null;

    public static MenuValidator getInstance() {
        synchronized (MenuValidator.class) {
            if (firstInstance == null) {
                firstInstance = new MenuValidator();
            }
            return firstInstance;
        }
    }

    @Override
    public void validateListMenuItems(List<MenuItemDto> menuItemDto) {
        MenuItemValidator itemValidator = MenuItemValidator.getInstance();
        itemValidator.validateListMenuItems(menuItemDto);
    }

    @Override
    public void validateMenuItem(MenuItemDto menuItemDto) {
        MenuItemValidator itemValidator = MenuItemValidator.getInstance();
        itemValidator.validateMenuItem(menuItemDto);
        if (!MenuService.getInstance().isIdValid(menuItemDto.getId())) {
            throw new IllegalArgumentException("Error code 400. Menu with such id do not exist");
        }
    }

    @Override
    public void validateMenu(MenuDto menuDto) {
        if (!MenuService.getInstance().isIdValid(menuDto.getId())) {
            throw new IllegalArgumentException("Error code 400. Menu with such id do not exist");
        }
        this.validateMenuRow(menuDto);
    }

    @Override
    public void validateMenuRow(MenuDto menuDto) {
        this.validateListMenuItems(menuDto.getItems());
        if (menuDto.getName() == null) {
            throw new IllegalArgumentException("Error code 400. Menu`s name is not valid");
        }
        if (menuDto.getEnable() == null) {
            throw new IllegalArgumentException("Error code 400. Menu`s enable status is not valid");
        }
    }

}
