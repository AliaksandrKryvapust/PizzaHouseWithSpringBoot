package groupId.artifactId.core.mapper;

import groupId.artifactId.core.dto.output.OrderDtoOutput;
import groupId.artifactId.core.dto.output.SelectedItemDtoOutput;
import groupId.artifactId.dao.entity.Order;
import groupId.artifactId.dao.entity.SelectedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OrderMapper {

    private final SelectedItemMapper selectedItemMapper;
    @Autowired
    public OrderMapper(SelectedItemMapper selectedItemMapper) {
        this.selectedItemMapper = selectedItemMapper;
    }

    public OrderDtoOutput outputMapping(Order order) {
        List<SelectedItemDtoOutput> items = new ArrayList<>();
        for (SelectedItem selectedItem : order.getSelectedItems()) {
            SelectedItemDtoOutput item = selectedItemMapper.outputMapping(selectedItem);
            items.add(item);
        }
        return new OrderDtoOutput(items, order.getId());
    }
}
