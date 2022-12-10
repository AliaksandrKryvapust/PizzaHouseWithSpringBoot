package groupId.artifactId.service.api;

import groupId.artifactId.dao.entity.MenuItem;
import java.util.List;

public interface IMenuItemService extends IService<MenuItem>,
        IServiceUpdate<MenuItem>, IServiceDelete {
    List<MenuItem> getListById(List<Long> ids);
    MenuItem saveInTransaction(MenuItem menuItem, Long menuId);
    MenuItem updateInTransaction(MenuItem menuItem, Long menuId, Long id, Integer version);
}
