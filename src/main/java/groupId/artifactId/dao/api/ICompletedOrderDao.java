package groupId.artifactId.dao.api;

import groupId.artifactId.dao.entity.CompletedOrder;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static groupId.artifactId.core.Constants.COMPLETED_ORDER_ENTITY_GRAPH;

@Repository
public interface ICompletedOrderDao extends JpaRepository<CompletedOrder, Long> {
    @Override
    @EntityGraph(value = COMPLETED_ORDER_ENTITY_GRAPH)
    @NonNull Optional<CompletedOrder> findById(@NonNull Long id);
    @EntityGraph(value = COMPLETED_ORDER_ENTITY_GRAPH)
    Optional<CompletedOrder> findCompletedOrderByTicket_Id(Long id);
}
