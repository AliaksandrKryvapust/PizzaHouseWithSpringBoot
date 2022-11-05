package groupId.artifactId.dao.api;

import groupId.artifactId.dao.entity.api.IPizzaInfo;

public interface IPizzaInfoDao extends IDao<IPizzaInfo>, IDaoUpdate<IPizzaInfo>, IDaoDelete {

    Boolean exist(Long id);

    Boolean doesPizzaExist(String name);
}
