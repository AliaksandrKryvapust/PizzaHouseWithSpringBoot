package groupId.artifactId.dao.api;

import groupId.artifactId.dao.entity.api.IMenu;
import groupId.artifactId.dao.entity.api.IMenuItem;

public interface IMenuDao extends IDao<IMenu>, IDaoUpdate<IMenu>, IDaoDelete {
    IMenu updateItems(IMenu menu, IMenuItem menuItem);
}

