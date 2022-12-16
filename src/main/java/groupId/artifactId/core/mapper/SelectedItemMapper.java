package groupId.artifactId.core.mapper;

import groupId.artifactId.core.dto.input.SelectedItemDtoInput;
import groupId.artifactId.core.dto.output.SelectedItemDtoOutput;
import groupId.artifactId.dao.entity.MenuItem;
import groupId.artifactId.dao.entity.SelectedItem;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SelectedItemMapper {

    public SelectedItem inputMapping(SelectedItemDtoInput input, List<MenuItem> menuItems) {
        return SelectedItem.builder()
                .menuItemId(input.getMenuItemId())
                .menuItem(menuItems.stream().filter((i) -> i.getId().equals(input.getMenuItemId())).findFirst().orElseThrow())
                .count(input.getCount()).build();
    }

    public SelectedItemDtoOutput outputMapping(SelectedItem item) {
        return SelectedItemDtoOutput.builder()
                .menuItemId(item.getMenuItemId())
                .id(item.getId())
                .count(item.getCount())
                .createdAt(item.getCreateAt())
                .build();
    }
}
