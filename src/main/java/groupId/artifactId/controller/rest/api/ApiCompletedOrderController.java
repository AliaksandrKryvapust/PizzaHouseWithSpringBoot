package groupId.artifactId.controller.rest.api;

import groupId.artifactId.core.dto.output.CompletedOrderDtoOutput;
import groupId.artifactId.core.dto.output.crud.CompletedOrderDtoCrudOutput;
import groupId.artifactId.exceptions.NoContentException;
import groupId.artifactId.service.api.ICompletedOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/completed_order")
public class ApiCompletedOrderController {
    private final ICompletedOrderService completedOrderService;
    private final Logger logger;

    @Autowired
    public ApiCompletedOrderController(ICompletedOrderService completedOrderService) {
        this.completedOrderService = completedOrderService;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    //Read POSITION
    //1)  Read item need ticket id param
    @GetMapping("/{id}")
    protected ResponseEntity<CompletedOrderDtoOutput> get(@PathVariable long id) {
        try {
            return ResponseEntity.ok(completedOrderService.getAllData(id));
        } catch (NoContentException e) {
            logger.error("/api/completed_order there is no content to fulfill doGet method " + e.getMessage() + "\t" + e.getCause());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("/api/completed_order crashed during doGet method" + e.getMessage() + "\t" + e.getCause());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Read POSITION
    //1) Read list
    @GetMapping
    protected ResponseEntity<List<CompletedOrderDtoCrudOutput>> getList() {
        try {
            return ResponseEntity.ok(completedOrderService.get());
        } catch (NoContentException e) {
            logger.error("/api/completed_order there is no content to fulfill doGet method " + e.getMessage() + "\t" + e.getCause());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("/api/completed_order crashed during doGet method" + e.getMessage() + "\t" + e.getCause());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}