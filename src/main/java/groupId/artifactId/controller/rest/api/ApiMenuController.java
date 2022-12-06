package groupId.artifactId.controller.rest.api;

import groupId.artifactId.controller.validator.api.IMenuValidator;
import groupId.artifactId.core.dto.input.MenuDtoInput;
import groupId.artifactId.core.dto.output.MenuDtoOutput;
import groupId.artifactId.core.dto.output.crud.MenuDtoCrudOutput;
import groupId.artifactId.exceptions.NoContentException;
import groupId.artifactId.service.api.IMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.OptimisticLockException;
import java.util.List;

//CRUD controller
//IMenu
@RestController
@RequestMapping("/api/menu")
public class ApiMenuController {
    private final IMenuService menuService;
    private final IMenuValidator menuValidator;
    private final Logger logger;

    @Autowired
    public ApiMenuController(IMenuService menuService, IMenuValidator menuValidator) {
        this.menuService = menuService;
        this.menuValidator = menuValidator;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    //Read POSITION
    //1) Read item need id param  (id = 1)
    @GetMapping("/{id}")
    protected ResponseEntity<MenuDtoOutput> get(@PathVariable long id) {
        try {
            return ResponseEntity.ok(menuService.getAllData(id));
        } catch (NoContentException e) {
            logger.error("/api/menu there is no content to fulfill doGet method " + e.getMessage() + "\t" + e.getCause());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("/api/menu crashed during doGet method" + e.getMessage() + "\t" + e.getCause());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Read POSITION
    //1) Read list
    @GetMapping
    protected ResponseEntity<List<MenuDtoCrudOutput>> getList() {
        try {
            return ResponseEntity.ok(menuService.get());
        } catch (NoContentException e) {
            logger.error("/api/menu there is no content to fulfill doGet method " + e.getMessage() + "\t" + e.getCause());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("/api/menu crashed during doGet method" + e.getMessage() + "\t" + e.getCause());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //CREATE POSITION
    //body json
    @PostMapping
    protected ResponseEntity<MenuDtoCrudOutput> post(@RequestBody MenuDtoInput menuDtoInput) {
        try {
            menuValidator.validate(menuDtoInput); //TODO
            return ResponseEntity.ok(menuService.save(menuDtoInput));
        } catch (NoContentException e) {
            logger.error("/api/menu there is no content to fulfill doPost method " + e.getMessage() + "\t" + e.getCause());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("/api/menu crashed during doPost method" + e.getMessage() + "\t" + e.getCause());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //UPDATE POSITION
    //need param id
    //need param version/date_update - optimistic lock
    //body json
    @PutMapping("/{id}/version/{version}")
    protected ResponseEntity<MenuDtoCrudOutput> put(@PathVariable long id, @PathVariable("version") int version,
                                                    @RequestBody MenuDtoInput menuDtoInput) {
        try {
            menuValidator.validate(menuDtoInput); //TODO
            return ResponseEntity.ok(menuService.update(menuDtoInput, String.valueOf(id), String.valueOf(version)));
        } catch (NoContentException e) {
            logger.error("/api/menu there is no content to fulfill doPut method " + e.getMessage() + "\t" + e.getCause());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (OptimisticLockException e) {
            logger.error("/api/menu optimistic lock during doPut method" + e.getMessage() + "\t" + e.getCause());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            logger.error("/api/menu crashed during doPut method" + e.getMessage() + "\t" + e.getCause());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //DELETE POSITION
    //need param id
    //param delete - true/false completely delete
    @DeleteMapping("/{id}/delete/{delete}")
    protected ResponseEntity<Object> delete(@PathVariable long id, @PathVariable("delete") boolean delete) {
        try {
            menuService.delete(String.valueOf(id), String.valueOf(delete));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoContentException e) {
            logger.error("/api/menu there is no content to fulfill doDelete method " + e.getMessage() + "\t" + e.getCause());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("/api/menu crashed during doDelete method" + e.getMessage() + "\t" + e.getCause());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
