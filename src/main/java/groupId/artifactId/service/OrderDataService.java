package groupId.artifactId.service;

import groupId.artifactId.core.dto.input.OrderDataDtoInput;
import groupId.artifactId.core.mapper.CompletedOrderMapper;
import groupId.artifactId.core.mapper.OrderStageMapper;
import groupId.artifactId.dao.api.IOrderDataDao;
import groupId.artifactId.dao.entity.OrderData;
import groupId.artifactId.dao.entity.OrderStage;
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
        OrderData orderData = createOrderDataFromInput(dtoInput);
        validateInput(orderData);
        orderDataDao.save(orderData);
    }

    private OrderData createOrderDataFromInput(OrderDataDtoInput dtoInput) {
        OrderStage inputOrderStages = this.orderStageMapper.inputMapping(dtoInput.getDescription());
        return OrderData.builder().orderHistory(singletonList(inputOrderStages))
                .ticket(dtoInput.getTicket()).build();
    }

    private void validateInput(OrderData orderData) {
        if (orderData.getId() != null) {
            throw new IllegalStateException("Order data id should be empty");
        }
    }

    @Override
    @Transactional
    public OrderData saveInTransaction(OrderDataDtoInput dtoInput) {
        OrderStage inputOrderStages = this.orderStageMapper.inputMapping(dtoInput.getDescription());
        OrderData orderData = addOrderStagesBeforeUpdate(dtoInput, inputOrderStages);
        OrderData orderDataOutput = this.orderDataDao.saveAndFlush(orderData);
        createCompletedOrder(inputOrderStages, orderDataOutput);
        return orderDataOutput;
    }

    private OrderData addOrderStagesBeforeUpdate(OrderDataDtoInput dtoInput, OrderStage inputOrderStages) {
        OrderData orderData = this.findOrderDataByTicketId(dtoInput.getTicketId());
        orderData.getOrderHistory().add(inputOrderStages);
        orderData.setDone(inputOrderStages.getDescription().equals(ORDER_FINISH_DESCRIPTION));
        return orderData;
    }

    private void createCompletedOrder(OrderStage inputOrderStages, OrderData orderDataOutput) {
        if (inputOrderStages.getDescription().equals(ORDER_FINISH_DESCRIPTION)) {
            this.completedOrderService.create(completedOrderMapper.inputMapping(orderDataOutput));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public OrderData findOrderDataByTicketId(Long id) {
        return this.orderDataDao.findOrderDataByTicket_Id(id).orElseThrow();
    }

    @Override
    public OrderData save(OrderData orderData) {
        return this.orderDataDao.save(orderData);
    }

    @Override
    public List<OrderData> get() {
        return this.orderDataDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderData get(Long id) {
        return this.orderDataDao.findById(id).orElseThrow();
    }
}
