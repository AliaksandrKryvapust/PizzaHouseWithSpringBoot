package groupId.artifactId.service;

import groupId.artifactId.core.dto.input.OrderDataDtoInput;
import groupId.artifactId.core.mapper.CompletedOrderMapper;
import groupId.artifactId.core.mapper.OrderStageMapper;
import groupId.artifactId.dao.api.IOrderDataDao;
import groupId.artifactId.dao.entity.OrderData;
import groupId.artifactId.dao.entity.api.IOrderData;
import groupId.artifactId.dao.entity.api.IOrderStage;
import groupId.artifactId.exceptions.DaoException;
import groupId.artifactId.exceptions.ServiceException;
import groupId.artifactId.service.api.ICompletedOrderService;
import groupId.artifactId.service.api.IOrderDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static groupId.artifactId.core.Constants.ORDER_FINISH_DESCRIPTION;
import static java.util.Collections.singletonList;

@Service
public class OrderDataService implements IOrderDataService {
    private final IOrderDataDao orderDataDao;
    private final OrderStageMapper orderStageMapper;
    private final ICompletedOrderService completedOrderService;
    private final CompletedOrderMapper completedOrderMapper;

    @Autowired
    public OrderDataService(IOrderDataDao orderDataDao, OrderStageMapper orderStageMapper,
                            ICompletedOrderService completedOrderService, CompletedOrderMapper completedOrderMapper) {
        this.orderDataDao = orderDataDao;
        this.orderStageMapper = orderStageMapper;
        this.completedOrderService = completedOrderService;
        this.completedOrderMapper = completedOrderMapper;
    }

    @Override
    public void create(OrderDataDtoInput dtoInput) {
        IOrderStage inputOrderStages = this.orderStageMapper.inputMapping(dtoInput.getDescription());
        OrderData orderData = OrderData.builder().orderHistory(singletonList(inputOrderStages))
                .ticket(dtoInput.getTicket()).build();
        orderDataDao.save(orderData);
    }

    @Override
    @Transactional
    public IOrderData saveInTransaction(OrderDataDtoInput dtoInput) {
        try {
            IOrderStage inputOrderStages = this.orderStageMapper.inputMapping(dtoInput.getDescription());
            OrderData orderData = (OrderData) this.orderDataDao.get(dtoInput.getTicketId());
            orderData.getOrderHistory().add(inputOrderStages);
            orderData.setDone(inputOrderStages.getDescription().equals(ORDER_FINISH_DESCRIPTION));
            IOrderData orderDataOutput = this.save(orderData);
            if (inputOrderStages.getDescription().equals(ORDER_FINISH_DESCRIPTION)) {
                this.completedOrderService.create(completedOrderMapper.inputMapping(orderDataOutput));
            }
            return orderDataOutput;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public IOrderData save(IOrderData orderData) {
        return this.orderDataDao.update(orderData);
    }

    @Override
    public List<IOrderData> get() {
        try {
            return this.orderDataDao.get();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public IOrderData get(Long id) {
        try {
            return this.orderDataDao.get(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
