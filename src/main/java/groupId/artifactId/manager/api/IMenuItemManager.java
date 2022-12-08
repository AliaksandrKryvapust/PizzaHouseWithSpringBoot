package groupId.artifactId.manager.api;

import groupId.artifactId.core.dto.input.MenuItemDtoInput;
import groupId.artifactId.core.dto.output.MenuItemDtoOutput;

public interface IMenuItemManager extends IManager<MenuItemDtoOutput, MenuItemDtoInput>,
        IManagerUpdate<MenuItemDtoOutput, MenuItemDtoInput>, IManagerDelete {
}
