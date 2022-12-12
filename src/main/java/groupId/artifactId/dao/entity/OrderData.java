package groupId.artifactId.dao.entity;

import lombok.*;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

import static groupId.artifactId.core.Constants.ORDER_DATA_ENTITY_GRAPH;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@NamedEntityGraph(name = ORDER_DATA_ENTITY_GRAPH,
        attributeNodes = {@NamedAttributeNode("orderHistory"), @NamedAttributeNode("ticket")})
@Table(name = "order_data", schema = "pizza_manager")
public class OrderData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "ticket_id", referencedColumnName = "id")
    @Setter
    private Ticket ticket;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "order_data_id", referencedColumnName = "id", nullable = false)
    @Setter
    private List<OrderStage> orderHistory;
    @Setter
    @org.hibernate.annotations.Generated(GenerationTime.INSERT)
    private Boolean done;
    @org.hibernate.annotations.Generated(GenerationTime.INSERT)
    private Instant creationDate;
    @Version
    private Integer version;
}
