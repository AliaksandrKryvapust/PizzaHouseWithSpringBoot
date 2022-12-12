package groupId.artifactId.dao.api;

import groupId.artifactId.dao.entity.Menu;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static groupId.artifactId.core.Constants.MENU_ENTITY_GRAPH;

@Repository
public interface IMenuDao extends JpaRepository<Menu, Long> {
    @EntityGraph(value = MENU_ENTITY_GRAPH)
    @NonNull Optional<Menu> findById(@NonNull Long id);
}

