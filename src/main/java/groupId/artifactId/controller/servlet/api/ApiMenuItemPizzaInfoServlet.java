package groupId.artifactId.controller.servlet.api;

import groupId.artifactId.controller.utils.IoC.JsonConverterSingleton;
import groupId.artifactId.controller.utils.JsonConverter;
import groupId.artifactId.core.Constants;
import groupId.artifactId.exceptions.NoContentException;
import groupId.artifactId.service.IoC.MenuItemServiceSingleton;
import groupId.artifactId.service.api.IMenuItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "MenuItemPizzaInfo", urlPatterns = "/api/menu_item/pizza_info")
public class ApiMenuItemPizzaInfoServlet extends HttpServlet {
    private final IMenuItemService menuItemService;
    private final Logger logger;
    private final JsonConverter jsonConverter;

    public ApiMenuItemPizzaInfoServlet() {
        this.menuItemService = MenuItemServiceSingleton.getInstance();
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.jsonConverter = JsonConverterSingleton.getInstance();
    }

    //Read POSITION
    //1) Read item with all enclosed data need id param  (id = 92)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String id = req.getParameter(Constants.PARAMETER_ID);
            if (id != null) {
                resp.getWriter().write(jsonConverter.fromMenuItemToJson(menuItemService.getAllData(Long.valueOf(id))));
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_PRECONDITION_FAILED);
            }
        } catch (NoContentException e) {
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            logger.error("/api/menu_item/pizza_info there is no content to fulfill doGet method " + e.getMessage() + "\t" + e.getCause() +
                    "\tresponse status: " + resp.getStatus());
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            logger.error("/api/menu_item/pizza_info crashed during doGet method" + e.getMessage() + "\t" + e.getCause() +
                    "\tresponse status: " + resp.getStatus());
        }
    }
}
