package groupId.artifactId.service.api;

import groupId.artifactId.dao.entity.api.IMenuItem;

import java.util.List;

public interface IMenuItemService extends IService<IMenuItem>,
        IServiceUpdate<IMenuItem>, IServiceDelete {
    List<IMenuItem> getListById(List<Long> ids);
    IMenuItem saveInTransaction(IMenuItem menuItem, Long menuId);
    IMenuItem updateInTransaction(IMenuItem menuItem, Long menuId, Long id, Integer version);
}
