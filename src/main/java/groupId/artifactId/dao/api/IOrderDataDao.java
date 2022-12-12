package groupId.artifactId.dao.api;

import groupId.artifactId.dao.entity.OrderData;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static groupId.artifactId.core.Constants.ORDER_DATA_ENTITY_GRAPH;

@Repository
public interface IOrderDataDao extends JpaRepository<OrderData, Long> {
    @Override
    @EntityGraph(value = ORDER_DATA_ENTITY_GRAPH)
    @NonNull Optional<OrderData> findById(@NonNull Long aLong);
    @EntityGraph(value = ORDER_DATA_ENTITY_GRAPH)
    Optional<OrderData> findOrderDataByTicket_Id(Long id);
}
