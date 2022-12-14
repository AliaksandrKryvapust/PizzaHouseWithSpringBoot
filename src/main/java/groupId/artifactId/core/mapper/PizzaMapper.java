package groupId.artifactId.core.mapper;

import groupId.artifactId.core.dto.output.PizzaDtoOutput;
import groupId.artifactId.dao.entity.Pizza;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PizzaMapper {

    public PizzaDtoOutput outputMapping(Pizza pizza){
        return PizzaDtoOutput.builder()
                .id(pizza.getId())
                .name(pizza.getName())
                .size(pizza.getSize())
                .build();
    }
}
