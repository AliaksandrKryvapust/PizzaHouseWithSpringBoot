package groupId.artifactId.service;

import groupId.artifactId.dao.api.IMenuDao;
import groupId.artifactId.dao.entity.api.IMenu;
import groupId.artifactId.dao.entity.api.IMenuItem;
import groupId.artifactId.exceptions.DaoException;
import groupId.artifactId.exceptions.ServiceException;
import groupId.artifactId.service.api.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MenuService implements IMenuService {
    private final IMenuDao dao;

    @Autowired
    public MenuService(IMenuDao dao) {
        this.dao = dao;
    }

    @Override
    public List<IMenu> get() {
        try {
            return this.dao.get();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public IMenu get(Long id) {
        try {
            return this.dao.get(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public IMenu update(IMenu menuInput, Long id, Integer version) {
        try {
            return this.dao.update(menuInput, id, version);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public IMenu updateItem(IMenu menu, IMenuItem menuItem) {
        try {
            return this.dao.updateItems(menu, menuItem);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void delete(Long id, Boolean delete) {
        try {
            this.dao.delete(id, delete);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public IMenu save(IMenu menu) {
        try {
            return this.dao.save(menu);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
