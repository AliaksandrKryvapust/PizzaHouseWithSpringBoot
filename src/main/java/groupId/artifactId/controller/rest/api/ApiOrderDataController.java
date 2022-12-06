package groupId.artifactId.controller.rest.api;

import groupId.artifactId.controller.validator.api.IOrderDataValidator;
import groupId.artifactId.core.dto.input.OrderDataDtoInput;
import groupId.artifactId.core.dto.output.OrderDataDtoOutput;
import groupId.artifactId.core.dto.output.crud.OrderDataDtoCrudOutput;
import groupId.artifactId.dao.entity.api.ITicket;
import groupId.artifactId.exceptions.NoContentException;
import groupId.artifactId.service.api.IOrderDataService;
import groupId.artifactId.service.api.IOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order_data")
public class ApiOrderDataController {
    private final IOrderDataService orderDataService;
    private final IOrderService orderService;
    private final IOrderDataValidator orderDataValidator;
    private final Logger logger;

    @Autowired
    public ApiOrderDataController(IOrderDataService orderDataService, IOrderDataValidator orderDataValidator,
                                  IOrderService orderService) {
        this.orderDataService = orderDataService;
        this.orderDataValidator = orderDataValidator;
        this.orderService = orderService;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    //Read POSITION
    //1) Read item need ticket id param
    @GetMapping("/{id}")
    protected ResponseEntity<OrderDataDtoOutput> get(@PathVariable long id) {
        try {
            return ResponseEntity.ok(orderDataService.getAllData(id));
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
            return ResponseEntity.ok(orderDataService.get());
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
    protected ResponseEntity<OrderDataDtoCrudOutput> post(@RequestBody OrderDataDtoInput orderDataDtoInput) {
        try {
            orderDataValidator.validate(orderDataDtoInput); //TODO
            ITicket ticket = this.orderService.getRow(orderDataDtoInput.getTicketId());
            OrderDataDtoInput orderData = OrderDataDtoInput.builder().ticketId(orderDataDtoInput.getTicketId())
                    .description(orderDataDtoInput.getDescription()).ticket(ticket).build();
            return ResponseEntity.ok(orderDataService.save(orderData));
        } catch (NoContentException e) {
            logger.error("/api/order_data there is no content to fulfill doPost method " + e.getMessage() + "\t" + e.getCause());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("/api/order_data crashed during doPost method" + e.getMessage() + "\t" + e.getCause());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
