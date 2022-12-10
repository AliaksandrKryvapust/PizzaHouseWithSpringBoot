package groupId.artifactId.dao.api;

import groupId.artifactId.dao.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMenuItemDao extends JpaRepository<MenuItem, Long> {
}
