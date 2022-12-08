package groupId.artifactId.service;

import groupId.artifactId.core.dto.input.OrderDataDtoInput;
import groupId.artifactId.dao.api.ITicketDao;
import groupId.artifactId.dao.entity.api.ITicket;
import groupId.artifactId.exceptions.DaoException;
import groupId.artifactId.exceptions.ServiceException;
import groupId.artifactId.service.api.IOrderDataService;
import groupId.artifactId.service.api.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static groupId.artifactId.core.Constants.ORDER_START_DESCRIPTION;

@Service
public class OrderService implements IOrderService {
    private final ITicketDao ticketDao;
    private final IOrderDataService orderDataService;

    @Autowired
    public OrderService(ITicketDao ticketDao, IOrderDataService orderDataService) {
        this.ticketDao = ticketDao;
        this.orderDataService = orderDataService;
    }

    @Override
    @Transactional
    public ITicket save(ITicket orderDtoInput) {
        try {
            ITicket ticket = this.ticketDao.save(orderDtoInput);
            OrderDataDtoInput dtoInput = OrderDataDtoInput.builder().ticketId(ticket.getId())
                    .description(ORDER_START_DESCRIPTION).ticket(ticket).build();
            orderDataService.create(dtoInput);
            return ticket;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<ITicket> get() {
        try {
            return this.ticketDao.get();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public ITicket get(Long id) {
        try {
            return this.ticketDao.get(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
