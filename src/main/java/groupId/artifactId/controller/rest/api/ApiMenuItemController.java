package groupId.artifactId.controller.rest.api;

import groupId.artifactId.controller.validator.api.IMenuItemValidator;
import groupId.artifactId.core.dto.input.MenuItemDtoInput;
import groupId.artifactId.core.dto.output.MenuItemDtoOutput;
import groupId.artifactId.exceptions.NoContentException;
import groupId.artifactId.service.api.IMenuItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.OptimisticLockException;
import java.util.List;

//CRUD controller
//IMenuItem
@RestController
@RequestMapping("/api/menu_item")
public class ApiMenuItemController {
    private final IMenuItemService menuItemService;
    private final IMenuItemValidator menuItemValidator;
    private final Logger logger;

    @Autowired
    public ApiMenuItemController(IMenuItemService menuItemService, IMenuItemValidator menuItemValidator) {
        this.menuItemService = menuItemService;
        this.menuItemValidator = menuItemValidator;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    //Read POSITION
    //1) Read item need id param
    @GetMapping("/{id}")
    protected ResponseEntity<MenuItemDtoOutput> get(@PathVariable long id) {
        try {
            return ResponseEntity.ok(menuItemService.get(id));
        } catch (NoContentException e) {
            logger.error("/api/menu_item there is no content to fulfill doGet method " + e.getMessage() + "\t" + e.getCause());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("/api/menu_item crashed during doGet method" + e.getMessage() + "\t" + e.getCause());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Read POSITION
    //1) Read list
    @GetMapping
    protected ResponseEntity<List<MenuItemDtoOutput>> getList() {
        try {
            return ResponseEntity.ok(menuItemService.get());
        } catch (NoContentException e) {
            logger.error("/api/menu_item there is no content to fulfill doGet method " + e.getMessage() + "\t" + e.getCause());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("/api/menu_item crashed during doGet method" + e.getMessage() + "\t" + e.getCause());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //CREATE POSITION
    //body json
    @PostMapping
    protected ResponseEntity<MenuItemDtoOutput> post(@RequestBody MenuItemDtoInput dtoInput) {
        try {
            menuItemValidator.validate(dtoInput); // TODO
            return ResponseEntity.ok(this.menuItemService.save(dtoInput));
        } catch (NoContentException e) {
            logger.error("/api/menu_item there is no content to fulfill doPost method " + e.getMessage() + "\t" + e.getCause());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("/api/menu_item crashed during doPost method" + e.getMessage() + "\t" + e.getCause());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //UPDATE POSITION
    //need param id
    //need param version/date_update - optimistic lock
    //body json
    @PutMapping("/{id}/version/{version}")
    protected ResponseEntity<MenuItemDtoOutput> put(@PathVariable long id, @PathVariable("version") int version,
                                                    @RequestBody MenuItemDtoInput dtoInput) {
        try {
            menuItemValidator.validate(dtoInput); // TODO
            return ResponseEntity.ok(this.menuItemService.update(dtoInput, String.valueOf(id), String.valueOf(version)));
        } catch (NoContentException e) {
            logger.error("/api/menu_item there is no content to fulfill doPut method " + e.getMessage() + "\t" + e.getCause());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (OptimisticLockException e) {
            logger.error("/api/menu_item optimistic lock during doPut method" + e.getMessage() + "\t" + e.getCause());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            logger.error("/api/menu_item crashed during doPut method" + e.getMessage() + "\t" + e.getCause());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //DELETE POSITION
    //need param id
    //param delete - true completely delete/false delete menu_id
    @DeleteMapping("/{id}/delete/{delete}")
    protected ResponseEntity<Object> delete(@PathVariable long id, @PathVariable("delete") boolean delete) {
        try {
            menuItemService.delete(String.valueOf(id), String.valueOf(delete));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoContentException e) {
            logger.error("/api/menu_item there is no content to fulfill doDelete method " + e.getMessage() + "\t" + e.getCause());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("/api/menu_item crashed during doDelete method" + e.getMessage() + "\t" + e.getCause());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
