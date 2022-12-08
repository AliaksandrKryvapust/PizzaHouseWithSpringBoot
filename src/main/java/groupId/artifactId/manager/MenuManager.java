package groupId.artifactId.manager;

import groupId.artifactId.core.dto.input.MenuDtoInput;
import groupId.artifactId.core.dto.output.MenuDtoOutput;
import groupId.artifactId.core.dto.output.crud.MenuDtoCrudOutput;
import groupId.artifactId.core.mapper.MenuMapper;
import groupId.artifactId.dao.entity.api.IMenu;
import groupId.artifactId.exceptions.NoContentException;
import groupId.artifactId.exceptions.ServiceException;
import groupId.artifactId.manager.api.IMenuManager;
import groupId.artifactId.service.api.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.OptimisticLockException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MenuManager implements IMenuManager {

    private final IMenuService menuService;
    private final MenuMapper menuMapper;

    @Autowired
    public MenuManager(IMenuService menuService, MenuMapper menuMapper) {
        this.menuService = menuService;
        this.menuMapper = menuMapper;
    }

    @Override
    public MenuDtoOutput getAllData(Long id) {
        try {
            IMenu menu = this.menuService.get(id);
            return menuMapper.outputMapping(menu);
        } catch (NoContentException e) {
            throw new NoContentException(e.getMessage());
        } catch (Exception e) {
            throw new ServiceException("Failed to get Menu at Service by id" + id + "\tcause" + e.getMessage(), e);
        }
    }

    @Override
    public MenuDtoCrudOutput save(MenuDtoInput menuDtoInput) {
        try {
            IMenu menu = this.menuService.save(menuMapper.inputMapping(menuDtoInput));
            return menuMapper.outputCrudMapping(menu);
        } catch (NoContentException e) {
            throw new NoContentException(e.getMessage());
        } catch (Exception e) {
            throw new ServiceException("Failed to save Menu at Service" + menuDtoInput + "\tcause:"
                    + e.getMessage(), e);
        }
    }

    @Override
    public List<MenuDtoCrudOutput> get() {
        try {
            return this.menuService.get().stream().map(menuMapper::outputCrudMapping)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException("Failed to get List of Menu`s at Service\tcause" + e.getMessage(), e);
        }
    }

    @Override
    public MenuDtoCrudOutput get(Long id) {
        try {
            IMenu menu = this.menuService.get(id);
            return menuMapper.outputCrudMapping(menu);
        } catch (NoContentException e) {
            throw new NoContentException(e.getMessage());
        } catch (Exception e) {
            throw new ServiceException("Failed to get Menu at Service by id" + id + "\tcause" + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Long id, Boolean delete) {
        try {
            this.menuService.delete(id, delete);
        } catch (NoContentException e) {
            throw new NoContentException(e.getMessage());
        } catch (Exception e) {
            throw new ServiceException("Failed to delete Menu with id:" + id, e);
        }
    }

    @Override
    public MenuDtoCrudOutput update(MenuDtoInput menuDtoInput, Long id, Integer version) {
        try {
            IMenu menu = this.menuService.update(menuMapper.inputMapping(menuDtoInput), id, version);
            return menuMapper.outputCrudMapping(menu);
        } catch (javax.persistence.OptimisticLockException e) {
            throw new OptimisticLockException(e.getMessage());
        } catch (NoContentException e) {
            throw new NoContentException(e.getMessage());
        } catch (Exception e) {
            throw new ServiceException("Failed to update Menu at Service " + menuDtoInput + "by id:" + id
                    + "\tcause" + e.getMessage(), e);
        }
    }
}
