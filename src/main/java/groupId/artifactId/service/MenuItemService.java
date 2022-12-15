package groupId.artifactId.service;

import groupId.artifactId.dao.api.IMenuItemDao;
import groupId.artifactId.dao.entity.Menu;
import groupId.artifactId.dao.entity.MenuItem;
import groupId.artifactId.dao.entity.PizzaInfo;
import groupId.artifactId.service.api.IMenuItemService;
import groupId.artifactId.service.api.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.OptimisticLockException;
import java.util.List;

@Service
public class MenuItemService implements IMenuItemService {
    private final IMenuItemDao menuItemDao;
    private final IMenuService menuService;

    @Autowired
    public MenuItemService(IMenuItemDao menuItemDao, IMenuService menuService) {
        this.menuItemDao = menuItemDao;
        this.menuService = menuService;
    }

    @Override
    @Transactional
    public MenuItem save(MenuItem menuItem) {
        validateInput(menuItem);
        MenuItem savedMenuItem = this.menuItemDao.save(menuItem);
        saveMenuItemMenuId(savedMenuItem);
        return savedMenuItem;
    }

    private void saveMenuItemMenuId(MenuItem savedMenuItem) {
        if (savedMenuItem.getMenuId() != null) {
            Menu menu = menuService.get(savedMenuItem.getMenuId());
            this.menuService.updateItem(menu, savedMenuItem);
        }
    }

    @Override
    @Transactional
    public MenuItem update(MenuItem menuItem, Long id, Integer version) {
        validateInput(menuItem);
        MenuItem currentEntity = this.menuItemDao.findById(id).orElseThrow();
        if (!currentEntity.getVersion().equals(version)) {
            throw new OptimisticLockException("menu_item table update failed, version does not match update denied");
        }
        updateMenuItemFields(menuItem, currentEntity);
        MenuItem savedMenuItem = this.menuItemDao.save(currentEntity);
        saveMenuItemMenuId(savedMenuItem);
        return savedMenuItem;
    }

    private void validateInput(MenuItem menuItem) {
        if (menuItem.getId() != null || menuItem.getVersion() != null) {
            throw new IllegalStateException("MenuItem id & version should be empty");
        }
    }

    private void updateMenuItemFields(MenuItem menuItem, MenuItem currentEntity) {
        PizzaInfo currentPizzaInfo = currentEntity.getPizzaInfo();
        currentPizzaInfo.setName(menuItem.getPizzaInfo().getName());
        currentPizzaInfo.setDescription(menuItem.getPizzaInfo().getDescription());
        currentPizzaInfo.setSize(menuItem.getPizzaInfo().getSize());
        currentEntity.setPrice(menuItem.getPrice());
        currentEntity.setPizzaInfo(currentPizzaInfo);
    }

    @Override
    public List<MenuItem> get() {
        return this.menuItemDao.findAll();
    }

    @Override
    public MenuItem get(Long id) {
        return this.menuItemDao.findById(id).orElseThrow();
    }

    @Override
    public List<MenuItem> getListById(List<Long> ids) {
        return this.menuItemDao.findAllById(ids);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        this.menuItemDao.deleteById(id);
    }
}
