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
@Table(name = "selected_item", schema = "pizza_manager")
public class SelectedItem{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "menu_item_id", referencedColumnName = "id")
    @Setter
    private MenuItem menuItem;
    @Setter
    private Integer count;
    @org.hibernate.annotations.Generated(GenerationTime.INSERT)
    private Instant createAt;
}
