package groupId.artifactId.manager;

import groupId.artifactId.core.dto.input.OrderDataDtoInput;
import groupId.artifactId.core.dto.output.OrderDataDtoOutput;
import groupId.artifactId.core.dto.output.crud.OrderDataDtoCrudOutput;
import groupId.artifactId.core.mapper.OrderDataMapper;
import groupId.artifactId.dao.entity.api.IOrderData;
import groupId.artifactId.dao.entity.api.ITicket;
import groupId.artifactId.exceptions.NoContentException;
import groupId.artifactId.exceptions.ServiceException;
import groupId.artifactId.manager.api.IOrderDataManager;
import groupId.artifactId.service.api.IOrderDataService;
import groupId.artifactId.service.api.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderDataManager implements IOrderDataManager {
    private final IOrderDataService orderDataService;
    private final IOrderService orderService;
    private final OrderDataMapper orderDataMapper;

    @Autowired
    public OrderDataManager(IOrderDataService orderDataService, IOrderService orderService,
                            OrderDataMapper orderDataMapper) {
        this.orderDataService = orderDataService;
        this.orderService = orderService;
        this.orderDataMapper = orderDataMapper;
    }

    @Override
    public OrderDataDtoCrudOutput save(OrderDataDtoInput orderDataDtoInput) {
        try {
            ITicket ticket = this.orderService.get(orderDataDtoInput.getTicketId());
            OrderDataDtoInput orderData = OrderDataDtoInput.builder().ticketId(orderDataDtoInput.getTicketId())
                    .description(orderDataDtoInput.getDescription()).ticket(ticket).build();
            IOrderData orderDataOutput = this.orderDataService.saveInTransaction(orderData);
            return orderDataMapper.outputCrudMapping(orderDataOutput);
        } catch (NoContentException e) {
            throw new NoContentException(e.getMessage());
        } catch (Exception e) {
            throw new ServiceException("Failed to save Order Data" + orderDataDtoInput, e);
        }
    }

    @Override
    public List<OrderDataDtoCrudOutput> get() {
        try {
            return this.orderDataService.get().stream().map(orderDataMapper::outputCrudMapping).collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException("Failed to get Order Data at Service", e);
        }
    }

    @Override
    public OrderDataDtoCrudOutput get(Long id) {
        try {
            return orderDataMapper.outputCrudMapping(this.orderDataService.get(id));
        } catch (NoContentException e) {
            throw new NoContentException(e.getMessage());
        } catch (Exception e) {
            throw new ServiceException("Failed to get Order Data at Service by Ticket id" + id, e);
        }
    }

    @Override
    public OrderDataDtoOutput getAllData(Long id) {
        try {
            return orderDataMapper.outputMapping(this.orderDataService.get(id));
        } catch (NoContentException e) {
            throw new NoContentException(e.getMessage());
        } catch (Exception e) {
            throw new ServiceException("Failed to getAll data from Order Data at Service by Ticket id" + id, e);
        }
    }
}
