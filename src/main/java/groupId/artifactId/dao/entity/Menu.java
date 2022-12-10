package groupId.artifactId.dao.entity;

import lombok.*;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@NamedEntityGraph(name = "Menu.items", attributeNodes = @NamedAttributeNode("items"))
@Table(name = "menu", schema = "pizza_manager")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany
    @JoinColumn(name = "menu_id", referencedColumnName = "id")
    @Setter
    private List<MenuItem> items;
    @Setter
    private String name;
    @Setter
    private Boolean enable;
    @org.hibernate.annotations.Generated(GenerationTime.INSERT)
    private Instant creationDate;
    @Version
    private Integer version;
}
