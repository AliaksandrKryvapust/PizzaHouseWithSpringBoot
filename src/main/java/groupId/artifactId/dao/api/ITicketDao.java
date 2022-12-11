package groupId.artifactId.dao.api;

import groupId.artifactId.dao.entity.Ticket;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITicketDao extends JpaRepository<Ticket, Long> {
    @EntityGraph(value = "ticket.order.selectedItems")
    @NonNull Optional<Ticket> findById(@NonNull Long id);
}
