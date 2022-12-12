package groupId.artifactId.dao.api;

import groupId.artifactId.dao.entity.Ticket;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static groupId.artifactId.core.Constants.TICKET_ENTITY_GRAPH;

@Repository
public interface ITicketDao extends JpaRepository<Ticket, Long> {
    @EntityGraph(value = TICKET_ENTITY_GRAPH)
    @NonNull Optional<Ticket> findById(@NonNull Long id);
}
