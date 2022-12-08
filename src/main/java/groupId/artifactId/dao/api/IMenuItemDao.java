package groupId.artifactId.dao.api;

import groupId.artifactId.dao.entity.api.IMenuItem;

import java.util.List;

public interface IMenuItemDao extends IDao<IMenuItem>, IDaoUpdate<IMenuItem>, IDaoDelete {
    List<IMenuItem> getListById(List<Long> ids);
}
