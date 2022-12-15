package groupId.artifactId.dao.entity;

import lombok.*;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.time.Instant;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "menu_item", schema = "pizza_manager")
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name ="menu_id", insertable = false, updatable = false)
    private Long menuId;
    @Embedded
    @Setter
    private PizzaInfo pizzaInfo;
    @Setter
    private Double price;
    @Generated(GenerationTime.INSERT)
    private Instant creationDate;
    @Version
    private Integer version;
}
