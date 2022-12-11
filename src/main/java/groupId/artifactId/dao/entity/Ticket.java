package groupId.artifactId.dao.entity;

import lombok.*;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.time.Instant;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@NamedEntityGraph(
        name = "ticket.order.selectedItems",
        attributeNodes = @NamedAttributeNode(value = "order", subgraph = "order.selectedItems"),
        subgraphs = {
                @NamedSubgraph(name = "order.selectedItems",
                        attributeNodes = @NamedAttributeNode(value = "selectedItems", subgraph = "selectedItems.items")),
                @NamedSubgraph(name = "selectedItems.items", attributeNodes = @NamedAttributeNode(value = "menuItem"))
        })
@Table(name = "ticket", schema = "pizza_manager")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @Setter
    private Order order;
    @org.hibernate.annotations.Generated(GenerationTime.INSERT)
    private Instant createAt;
}
