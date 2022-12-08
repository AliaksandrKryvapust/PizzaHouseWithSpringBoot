package groupId.artifactId.service;

import groupId.artifactId.dao.api.IMenuItemDao;
import groupId.artifactId.dao.entity.MenuItem;
import groupId.artifactId.dao.entity.api.IMenu;
import groupId.artifactId.dao.entity.api.IMenuItem;
import groupId.artifactId.exceptions.DaoException;
import groupId.artifactId.exceptions.ServiceException;
import groupId.artifactId.service.api.IMenuItemService;
import groupId.artifactId.service.api.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Component
public class MenuItemService implements IMenuItemService {
    private final IMenuItemDao menuItemDao;
    private final IMenuService menuService;

    @Autowired
    public MenuItemService(IMenuItemDao menuItemDao, IMenuService menuService) {
        this.menuItemDao = menuItemDao;
        this.menuService = menuService;
    }

    @Override
    public IMenuItem save(IMenuItem menuItem) {
        return this.menuItemDao.save(menuItem);
    }

    @Override
    @Transactional
    public IMenuItem saveInTransaction(IMenuItem menuItem, Long menuId) {
        try {
            IMenuItem savedMenuItem = this.save(menuItem);
            if (menuId != null) {
                IMenu menu = menuService.get(menuId);
                List<IMenuItem> items = menuService.updateItem(menu, savedMenuItem).getItems();
                return Objects.requireNonNull(items.stream()
                        .filter((i) -> i.getPizzaInfo().getName().equals(menuItem.getPizzaInfo().getName()))
                        .findFirst().orElse(new MenuItem()));
            } else {
                return savedMenuItem;
            }
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public IMenuItem update(IMenuItem menuItem, Long id, Integer version) {
        return this.menuItemDao.update(menuItem, id, version);
    }

    @Override
    @Transactional
    public IMenuItem updateInTransaction(IMenuItem menuItem, Long menuId, Long id, Integer version) {
        try {
            IMenuItem savedMenuItem = this.update(menuItem, id, version);
            if (menuId != null) {
                IMenu menu = menuService.get(savedMenuItem.getId());
                List<IMenuItem> items = menuService.updateItem(menu, savedMenuItem).getItems();
                return Objects.requireNonNull(items.stream()
                        .filter((i) -> i.getPizzaInfo().getName().equals(savedMenuItem.getPizzaInfo().getName()))
                        .findFirst().orElse(new MenuItem()));
            } else {
                return savedMenuItem;
            }
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<IMenuItem> get() {
        try {
            return this.menuItemDao.get();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public IMenuItem get(Long id) {
        try {
            return this.menuItemDao.get(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<IMenuItem> getListById(List<Long> ids) {
        return this.menuItemDao.getListById(ids);
    }

    @Override
    @Transactional
    public void delete(Long id, Boolean delete) {
        try {
            this.menuItemDao.delete(id, delete);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
