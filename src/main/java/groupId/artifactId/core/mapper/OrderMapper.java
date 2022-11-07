package groupId.artifactId.core.mapper;

import groupId.artifactId.core.dto.input.OrderDtoInput;
import groupId.artifactId.core.dto.input.SelectedItemDtoInput;
import groupId.artifactId.core.dto.output.OrderDtoOutput;
import groupId.artifactId.core.dto.output.SelectedItemDtoOutput;
import groupId.artifactId.dao.entity.Order;
import groupId.artifactId.dao.entity.api.IOrder;
import groupId.artifactId.dao.entity.api.ISelectedItem;

import java.util.ArrayList;
import java.util.List;

public class OrderMapper {
    public static IOrder orderInputMapping(OrderDtoInput input, Long orderId) {
        List<ISelectedItem> items = new ArrayList<>();
        for (SelectedItemDtoInput selectedItem : input.getSelectedItems()) {
            ISelectedItem item = SelectedItemMapper.selectedItemInputMapping(selectedItem, orderId);
            items.add(item);
        }
        return new Order(items, orderId);
    }

    public static OrderDtoOutput orderOutputMapping(IOrder order) {
        List<SelectedItemDtoOutput> items = new ArrayList<>();
        for (ISelectedItem selectedItem : order.getSelectedItems()) {
            SelectedItemDtoOutput item = SelectedItemMapper.selectedItemOutputMapping(selectedItem);
            items.add(item);
        }
        return new OrderDtoOutput(items, order.getId(), order.getCreationDate(), order.getVersion());
    }
}