package groupId.artifactId.manager.api;

import groupId.artifactId.dao.entity.*;

public interface IPizzaHouse {
    Menu getMenu();
    Ticket create(Order order);
    OrderData getOrderDataByToken(Ticket token);
    CompletedOrder getByToken(Ticket token);
}
