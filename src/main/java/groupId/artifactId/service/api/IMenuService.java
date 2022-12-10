package groupId.artifactId.service.api;

import groupId.artifactId.dao.entity.Menu;
import groupId.artifactId.dao.entity.MenuItem;

public interface IMenuService extends IService<Menu>, IServiceUpdate<Menu>, IServiceDelete {
    Menu updateItem(Menu menu, MenuItem menuItem);
}
