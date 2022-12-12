package groupId.artifactId.manager;

import groupId.artifactId.core.dto.input.OrderDtoInput;
import groupId.artifactId.core.dto.input.SelectedItemDtoInput;
import groupId.artifactId.core.dto.output.TicketDtoOutput;
import groupId.artifactId.core.dto.output.crud.TicketDtoCrudOutput;
import groupId.artifactId.core.mapper.SelectedItemMapper;
import groupId.artifactId.core.mapper.TicketMapper;
import groupId.artifactId.dao.entity.MenuItem;
import groupId.artifactId.dao.entity.Order;
import groupId.artifactId.dao.entity.SelectedItem;
import groupId.artifactId.dao.entity.Ticket;
import groupId.artifactId.exceptions.NoContentException;
import groupId.artifactId.exceptions.ServiceException;
import groupId.artifactId.manager.api.IOrderManager;
import groupId.artifactId.service.api.IMenuItemService;
import groupId.artifactId.service.api.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderManager implements IOrderManager {
    private final IOrderService orderService;
    private final TicketMapper ticketMapper;
    private final SelectedItemMapper selectedItemMapper;
    private final IMenuItemService menuItemService;

    @Autowired
    public OrderManager(IOrderService orderService, TicketMapper ticketMapper, SelectedItemMapper selectedItemMapper,
                        IMenuItemService menuItemService) {
        this.orderService = orderService;
        this.ticketMapper = ticketMapper;
        this.selectedItemMapper = selectedItemMapper;
        this.menuItemService = menuItemService;
    }

    @Override
    public TicketDtoCrudOutput save(OrderDtoInput orderDtoInput) {
        try {
            List<Long> menuItemsId = orderDtoInput.getSelectedItems().stream().map(SelectedItemDtoInput::getMenuItemId)
                    .collect(Collectors.toList());
            List<MenuItem> menuItems = this.menuItemService.getListById(menuItemsId);
            List<SelectedItem> inputSelectedItems = orderDtoInput.getSelectedItems().stream()
                    .map((i) -> selectedItemMapper.inputMapping(i, menuItems)).collect(Collectors.toList());
            Order newOrder = Order.builder().selectedItems(inputSelectedItems).build();
            Ticket ticket = this.orderService.save(Ticket.builder().order(newOrder).build());
            return ticketMapper.outputCrudMapping(ticket);
        } catch (NoContentException e) {
            throw new NoContentException(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            throw new NoContentException("order table or order data table save failed, check preconditions and FK values");
        } catch (Exception e) {
            throw new ServiceException("Failed to save Order" + orderDtoInput, e);
        }
    }

    @Override
    public List<TicketDtoCrudOutput> get() {
        try {
            return this.orderService.get().stream().map(ticketMapper::outputCrudMapping)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException("Failed to get List of Ticket`s at Service", e);
        }
    }

    @Override
    public TicketDtoCrudOutput get(Long id) {
        try {
            return ticketMapper.outputCrudMapping(this.orderService.get(id));
        } catch (NoContentException e) {
            throw new NoContentException(e.getMessage());
        } catch (Exception e) {
            throw new ServiceException("Failed to get Ticket at Service by id" + id, e);
        }
    }

    @Override
    public TicketDtoOutput getAllData(Long id) {
        try {
            Ticket ticket = this.orderService.get(id);
            return ticketMapper.outputMapping(ticket);
        } catch (NoContentException e) {
            throw new NoContentException(e.getMessage());
        } catch (Exception e) {
            throw new ServiceException("Failed to getAll data from Ticket at Service by id" + id, e);
        }
    }
}
