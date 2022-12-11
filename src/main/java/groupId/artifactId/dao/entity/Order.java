package groupId.artifactId.dao.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "order_ticket", schema = "pizza_manager")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    @Setter
    private List<SelectedItem> selectedItems;
}
