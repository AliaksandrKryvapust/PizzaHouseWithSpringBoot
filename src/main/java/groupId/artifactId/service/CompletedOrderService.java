package groupId.artifactId.service;

import groupId.artifactId.dao.api.ICompletedOrderDao;
import groupId.artifactId.dao.entity.api.ICompletedOrder;
import groupId.artifactId.exceptions.DaoException;
import groupId.artifactId.exceptions.ServiceException;
import groupId.artifactId.service.api.ICompletedOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompletedOrderService implements ICompletedOrderService {
    private final ICompletedOrderDao completedOrderDao;

    @Autowired
    public CompletedOrderService(ICompletedOrderDao completedOrderDao) {
        this.completedOrderDao = completedOrderDao;
    }

    @Override
    public ICompletedOrder create(ICompletedOrder type) {
        return this.completedOrderDao.save(type);
    }

    @Override
    @Transactional
    public ICompletedOrder save(ICompletedOrder type) {
        try {
            return this.completedOrderDao.save(type);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<ICompletedOrder> get() {
        try {
            return this.completedOrderDao.get();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public ICompletedOrder get(Long id) {
        try {
            return this.completedOrderDao.get(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
