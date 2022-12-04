package groupId.artifactId.manager.api;

import groupId.artifactId.dao.entity.api.*;

public interface IPizzaHouse {
    IMenu getMenu();
    ITicket create(IOrder order);
    IOrderData getOrderDataByToken(ITicket token);
    ICompletedOrder getByToken(ITicket token);
}
