package groupId.artifactId.manager;

import groupId.artifactId.core.dto.input.MenuItemDtoInput;
import groupId.artifactId.core.dto.output.MenuItemDtoOutput;
import groupId.artifactId.core.mapper.MenuItemMapper;
import groupId.artifactId.dao.entity.api.IMenuItem;
import groupId.artifactId.exceptions.DaoException;
import groupId.artifactId.exceptions.NoContentException;
import groupId.artifactId.exceptions.ServiceException;
import groupId.artifactId.manager.api.IMenuItemManager;
import groupId.artifactId.service.api.IMenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.OptimisticLockException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MenuItemManager implements IMenuItemManager {

    private final MenuItemMapper menuItemMapper;
    private final IMenuItemService menuItemService;

    @Autowired
    public MenuItemManager(MenuItemMapper menuItemMapper, IMenuItemService menuItemService) {
        this.menuItemMapper = menuItemMapper;
        this.menuItemService = menuItemService;
    }

    @Override
    public MenuItemDtoOutput save(MenuItemDtoInput menuItemDtoInput) {
        try {
            IMenuItem menuItem = this.menuItemService.saveInTransaction(menuItemMapper.inputMapping(menuItemDtoInput),
                    menuItemDtoInput.getMenuId());
            return menuItemMapper.outputMapping(menuItem);
        } catch (NoContentException e) {
            throw new NoContentException(e.getMessage());
        } catch (Exception e) {
            throw new ServiceException("Failed to save Menu Item at Service" + menuItemDtoInput + "\tcause:"
                    + e.getMessage(), e);
        }
    }

    @Override
    public List<MenuItemDtoOutput> get() {
        try {
            return this.menuItemService.get().stream().map(menuItemMapper::outputMapping)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException("Failed to get List of Menu Item`s at Service\tcause" + e.getMessage(), e);
        }
    }

    @Override
    public MenuItemDtoOutput get(Long id) {
        try {
            return menuItemMapper.outputMapping(this.menuItemService.get(id));
        } catch (NoContentException e) {
            throw new NoContentException(e.getMessage());
        } catch (Exception e) {
            throw new ServiceException("Failed to get Menu Item at Service by id" + id + "\tcause" + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Long id, Boolean delete) {
        try {
            this.menuItemService.delete(id, delete);
        } catch (NoContentException e) {
            throw new NoContentException(e.getMessage());
        } catch (Exception e) {
            throw new ServiceException("Failed to delete Menu Item with id:" + id, e);
        }
    }

    @Override
    public MenuItemDtoOutput update(MenuItemDtoInput menuItemDtoInput, Long id, Integer version) {
        try {
            IMenuItem menuItem = this.menuItemService.updateInTransaction(menuItemMapper.inputMapping(menuItemDtoInput),
                    menuItemDtoInput.getMenuId(), id, version);
            return menuItemMapper.outputMapping(menuItem);
        } catch (OptimisticLockException e) {
            throw new OptimisticLockException(e.getMessage());
        } catch (NoContentException e) {
            throw new NoContentException(e.getMessage());
        } catch (Exception e) {
            throw new ServiceException("Failed to update Menu Item at Service " + menuItemDtoInput + "by id:" + id
                    + "\tcause" + e.getMessage(), e);
        }
    }
}
