package groupId.artifactId.controller.rest.api;

import groupId.artifactId.core.dto.input.OrderDataDtoInput;
import groupId.artifactId.core.dto.output.OrderDataDtoOutput;
import groupId.artifactId.core.dto.output.crud.OrderDataDtoCrudOutput;
import groupId.artifactId.exceptions.NoContentException;
import groupId.artifactId.manager.api.IOrderDataManager;
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
@RequestMapping("/api/order_data")
public class ApiOrderDataController {
    private final IOrderDataManager orderDataManager;
    private final Logger logger;

    @Autowired
    public ApiOrderDataController(IOrderDataManager orderDataManager) {
        this.orderDataManager = orderDataManager;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    //Read POSITION
    //1) Read item need id param
    @GetMapping("/{id}")
    protected ResponseEntity<OrderDataDtoOutput> get(@PathVariable long id) {
        try {
            return ResponseEntity.ok(orderDataManager.getAllData(id));
        } catch (NoContentException e) {
            logger.error("/api/order_data there is no content to fulfill doGet method " + e.getMessage() + "\t" + e.getCause());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("/api/order_data crashed during doGet method" + e.getMessage() + "\t" + e.getCause());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Read POSITION
    //1) Read item by ticket
    @GetMapping("/ticket/{id}")
    protected ResponseEntity<OrderDataDtoOutput> getByTicket(@PathVariable long id) {
        try {
            return ResponseEntity.ok(orderDataManager.getOrderDataByTicket(id));
        } catch (NoContentException e) {
            logger.error("/api/order_data there is no content to fulfill doGet method " + e.getMessage() + "\t" + e.getCause());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("/api/order_data crashed during doGet method" + e.getMessage() + "\t" + e.getCause());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Read POSITION
    //1) Read list
    @GetMapping
    protected ResponseEntity<List<OrderDataDtoCrudOutput>> getList() {
        try {
            return ResponseEntity.ok(orderDataManager.get());
        } catch (NoContentException e) {
            logger.error("/api/order_data there is no content to fulfill doGet method " + e.getMessage() + "\t" + e.getCause());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("/api/order_data crashed during doGet method" + e.getMessage() + "\t" + e.getCause());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //CREATE POSITION
    //body json
    @PostMapping
    protected ResponseEntity<OrderDataDtoCrudOutput> post(@Valid @RequestBody OrderDataDtoInput orderDataDtoInput) {
        try {
            return ResponseEntity.ok(orderDataManager.save(orderDataDtoInput));
        } catch (NoContentException e) {
            logger.error("/api/order_data there is no content to fulfill doPost method " + e.getMessage() + "\t" + e.getCause());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("/api/order_data crashed during doPost method" + e.getMessage() + "\t" + e.getCause());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
