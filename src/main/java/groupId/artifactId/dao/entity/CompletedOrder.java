package groupId.artifactId.dao.entity;

import lombok.*;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

import static groupId.artifactId.core.Constants.COMPLETED_ORDER_ENTITY_GRAPH;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@NamedEntityGraph(name = COMPLETED_ORDER_ENTITY_GRAPH,
        attributeNodes = {@NamedAttributeNode("items"), @NamedAttributeNode("ticket")})
@Table(name = "completed_order", schema = "pizza_manager")
public class CompletedOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "ticket_id", referencedColumnName = "id")
    @Setter
    private Ticket ticket;
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "completed_order_id", referencedColumnName = "id", nullable = false)
    @Setter
    private List<Pizza> items;
    @org.hibernate.annotations.Generated(GenerationTime.INSERT)
    private Instant creationDate;
}
