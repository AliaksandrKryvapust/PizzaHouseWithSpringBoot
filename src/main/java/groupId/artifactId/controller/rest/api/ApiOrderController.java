package groupId.artifactId.controller.rest.api;

import groupId.artifactId.core.dto.input.OrderDtoInput;
import groupId.artifactId.core.dto.output.TicketDtoOutput;
import groupId.artifactId.core.dto.output.crud.TicketDtoCrudOutput;
import groupId.artifactId.exceptions.NoContentException;
import groupId.artifactId.manager.api.IOrderManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/order")
public class ApiOrderController {
    private final IOrderManager orderManager;
    private final Logger logger;

    @Autowired
    public ApiOrderController(IOrderManager orderManager) {
        this.orderManager = orderManager;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    //Read POSITION
    //1) Read item need id param
    @GetMapping("/{id}")
    protected ResponseEntity<TicketDtoOutput> get(@PathVariable long id) {
        try {
            return ResponseEntity.ok(orderManager.getAllData(id));
        } catch (NoContentException e) {
            logger.error("/api/order there is no content to fulfill doGet method " + e.getMessage() + "\t" + e.getCause());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("/api/order crashed during doGet method" + e.getMessage() + "\t" + e.getCause());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Read POSITION
    //1) Read list
    @GetMapping
    protected ResponseEntity<List<TicketDtoCrudOutput>> getList() {
        try {
            return ResponseEntity.ok(orderManager.get());
        } catch (NoContentException e) {
            logger.error("/api/order there is no content to fulfill doGet method " + e.getMessage() + "\t" + e.getCause());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("/api/order crashed during doGet method" + e.getMessage() + "\t" + e.getCause());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //CREATE POSITION
    //body json
    @PostMapping
    protected ResponseEntity<TicketDtoCrudOutput> post(@Valid @RequestBody OrderDtoInput orderDtoInput) {
        try {
            return ResponseEntity.ok(orderManager.save(orderDtoInput));
        } catch (NoContentException e) {
            logger.error("/api/order there is no content to fulfill doPost method " + e.getMessage() + "\t" + e.getCause());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("/api/order crashed during doPost method" + e.getMessage() + "\t" + e.getCause());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
