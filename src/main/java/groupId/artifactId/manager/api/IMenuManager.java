package groupId.artifactId.manager.api;

import groupId.artifactId.core.dto.input.MenuDtoInput;
import groupId.artifactId.core.dto.output.MenuDtoOutput;
import groupId.artifactId.core.dto.output.crud.MenuDtoCrudOutput;

public interface IMenuManager extends IManager<MenuDtoCrudOutput, MenuDtoInput>, IManagerUpdate<MenuDtoCrudOutput, MenuDtoInput>,
        IManagerDelete {
    MenuDtoOutput getAllData(Long id);
}
