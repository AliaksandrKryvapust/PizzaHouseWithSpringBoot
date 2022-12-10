package groupId.artifactId.dao.api;

import groupId.artifactId.dao.entity.Menu;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IMenuDao extends JpaRepository<Menu, Long> {
    @EntityGraph(value = "Menu.items")
    @NonNull Optional<Menu> findById(@NonNull Long id);
}

