package groupId.artifactId.service.IoC;

import groupId.artifactId.core.mapper.*;
import groupId.artifactId.dao.IoC.TicketDaoSingleton;
import groupId.artifactId.dao.api.EntityManagerFactoryHibernate;
import groupId.artifactId.service.OrderService;
import groupId.artifactId.service.api.IOrderService;

public class OrderServiceSingleton {
    private final IOrderService orderService;
    private volatile static OrderServiceSingleton instance = null;

    public OrderServiceSingleton() {
        this.orderService = new OrderService(TicketDaoSingleton.getInstance(), OrderDataServiceSingleton.getInstance(),
                MenuItemServiceSingleton.getInstance(), EntityManagerFactoryHibernate.getEntityManager(), new TicketMapper(new OrderMapper(
                new SelectedItemMapper(new MenuItemMapper(new PizzaInfoMapper())))),
                new SelectedItemMapper(new MenuItemMapper(new PizzaInfoMapper())));
    }

    public static IOrderService getInstance() {
        synchronized (OrderServiceSingleton.class) {
            if (instance == null) {
                instance = new OrderServiceSingleton();
            }
        }
        return instance.orderService;
    }
}
