package groupId.artifactId.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import groupId.artifactId.core.dto.*;
import groupId.artifactId.dao.entity.api.IMenu;
import groupId.artifactId.dao.entity.api.IMenuItem;
import groupId.artifactId.dao.entity.api.IPizzaInfo;
import groupId.artifactId.exceptions.IncorrectJsonParseException;
import groupId.artifactId.storage.entity.api.ICompletedOrder;
import groupId.artifactId.storage.entity.api.IOrderData;
import groupId.artifactId.storage.entity.api.IToken;

import javax.servlet.ServletInputStream;
import java.io.IOException;
import java.util.List;

public class JsonConverter {
    public static String fromMenuListToJson(List<IMenu> menu) {
        try {
            return new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(menu);
        } catch (JsonProcessingException e) {
            throw new IncorrectJsonParseException("failed to write List IMenu as json", e);
        }
    }

    public static String fromMenuToJson(IMenu menu) {
        try {
            return new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(menu);
        } catch (JsonProcessingException e) {
            throw new IncorrectJsonParseException("failed to write IMenu as json", e);
        }
    }

    public static String fromMenuItemListToJson(List<IMenuItem> items) {
        try {
            return new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(items);
        } catch (JsonProcessingException e) {
            throw new IncorrectJsonParseException("failed to write List MenuItem as json", e);
        }
    }

    public static String fromMenuItemToJson(IMenuItem item) {
        try {
            return new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(item);
        } catch (JsonProcessingException e) {
            throw new IncorrectJsonParseException("failed to write IMenuItem as json", e);
        }
    }

    public static String fromPizzaInfoListToJson(List<IPizzaInfo> items) {
        try {
            return new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(items);
        } catch (JsonProcessingException e) {
            throw new IncorrectJsonParseException("failed to write List PizzaInfo as json", e);
        }
    }

    public static String fromPizzaInfoToJson(IPizzaInfo pizzaInfo) {
        try {
            return new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(pizzaInfo);
        } catch (JsonProcessingException e) {
            throw new IncorrectJsonParseException("failed to write IPizzaInfo as json",e);
        }
    }
    public static String fromTokenToJson(IToken token) {
        try {
            return new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(token);
        } catch (JsonProcessingException e) {
            throw new IncorrectJsonParseException("failed to write IToken as json", e);
        }
    }

    public static String fromOrderDataToJson(IOrderData orderData) {
        try {
            return new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(orderData);
        } catch (JsonProcessingException e) {
            throw new IncorrectJsonParseException("failed to write IOrderData as json", e);
        }
    }

    public static MenuDto fromJsonToMenu(ServletInputStream servletInputStream, String id, String version) {
        try {
            MenuDto menu = new ObjectMapper().readValue(servletInputStream, new TypeReference<>() {
            });
            menu.setId(Long.valueOf(id));
            menu.setVersion(Integer.valueOf(version));
            return menu;
        } catch (IOException e) {
            throw new IncorrectJsonParseException("failed to read servletInputStream of MenuDto.class", e);
        }
    }

    public static MenuDto fromJsonToMenuRow(ServletInputStream servletInputStream) {
        try {
            return new ObjectMapper().readValue(servletInputStream, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new IncorrectJsonParseException("failed to read servletInputStream of MenuDto.class", e);
        }
    }

    public static List<MenuItemDto> fromJsonToListMenuItem(ServletInputStream servletInputStream) {
        try {
            return new ObjectMapper().readValue(servletInputStream, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new IncorrectJsonParseException("failed to read servletInputStream of MenuItemDto[].class", e);
        }
    }

    public static MenuItemDto fromJsonToMenuItem(ServletInputStream servletInputStream) {
        try {
            return new ObjectMapper().readValue(servletInputStream, MenuItemDto.class);
        } catch (IOException e) {
            throw new IncorrectJsonParseException("failed to read servletInputStream of MenuItemDto.class",e);
        }
    }

    public static MenuItemDto fromJsonToMenuItemUpdate(ServletInputStream servletInputStream, String id, String version) {
        try {
            MenuItemDto menuItem = new ObjectMapper().readValue(servletInputStream, new TypeReference<>() {
            });
            menuItem.setId(Long.valueOf(id));
            menuItem.setVersion(Integer.valueOf(version));
            return menuItem;
        } catch (IOException e) {
            throw new IncorrectJsonParseException("failed to read servletInputStream of MenuItem.class", e);
        }
    }

    public static PizzaInfoDto fromJsonToPizzaInfo(ServletInputStream servletInputStream) {
        try {
            return new ObjectMapper().readValue(servletInputStream, PizzaInfoDto.class);
        } catch (IOException e) {
            throw new IncorrectJsonParseException("failed to read servletInputStream of PizzaInfoDto.class", e);
        }
    }

    public static PizzaInfoDto fromJsonToPizzaInfoUpdate(ServletInputStream servletInputStream, String id, String version) {
        try {
            PizzaInfoDto dto = new ObjectMapper().readValue(servletInputStream, new TypeReference<>() {
            });
            dto.setId(Long.valueOf(id));
            dto.setVersion(Integer.valueOf(version));
            return dto;
        } catch (IOException e) {
            throw new IncorrectJsonParseException("failed to read servletInputStream of PizzaInfoDto.class", e);
        }
    }
    public static OrderDto fromJsonToOrder(ServletInputStream servletInputStream) {
        try {
            return new ObjectMapper().readValue(servletInputStream, OrderDto.class);
        } catch (IOException e) {
            throw new IncorrectJsonParseException("failed to read servletInputStream of OrderDto.class", e);
        }
    }

    public static TokenDto fromJsonToToken(ServletInputStream servletInputStream) {
        try {
            return new ObjectMapper().readValue(servletInputStream, TokenDto.class);
        } catch (IOException e) {
            throw new IncorrectJsonParseException("failed to read servletInputStream of TokenDto.class",e);
        }
    }
    public static OrderStageDtoWithId fromJsonToOrderStageWithId(ServletInputStream servletInputStream)  {
        try {
            return new ObjectMapper().registerModule(new JavaTimeModule()).readValue(servletInputStream, OrderStageDtoWithId.class);
        } catch (IOException e) {
            throw new IncorrectJsonParseException("failed to read servletInputStream of OrderStageDtoWithId.class",e);
        }
    }
    public static OrderDataDto fromJsonToOrderData(ServletInputStream servletInputStream)  {
        try {
            return new ObjectMapper().registerModule(new JavaTimeModule()).readValue(servletInputStream, OrderDataDto.class);
        } catch (IOException e) {
            throw new IncorrectJsonParseException("failed to read servletInputStream of OrderDataDto.class",e);
        }
    }
    public static String fromCompletedOrderToJson(ICompletedOrder completedOrder) {
        try {
            return new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(completedOrder);
        } catch (JsonProcessingException e) {
            throw new IncorrectJsonParseException("failed to write IOrderData as json",e);
        }
    }
}
