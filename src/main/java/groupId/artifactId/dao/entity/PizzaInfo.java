package groupId.artifactId.dao.entity;

import lombok.*;

import javax.persistence.Embeddable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class PizzaInfo {
    private String name;
    private String description;
    private Integer size;

    @Override
    public String toString() {
        return "PizzaInfo{" +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", size=" + size +
                '}';
    }
}
