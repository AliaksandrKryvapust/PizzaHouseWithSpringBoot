package groupId.artifactId.controller.exceptions;

import groupId.artifactId.exceptions.NoContentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.OptimisticLockException;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger;

    @Autowired
    public RestExceptionHandler() {
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @ExceptionHandler({NoContentException.class})
    public ResponseEntity<Object> handleNoContent(Exception ex, WebRequest request) {
        this.makeLog(ex);
        return handleExceptionInternal(ex, "There is no such content", new HttpHeaders(),
                HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleBadRequest(Exception ex, WebRequest request) {
        this.makeLog(ex);
        return handleExceptionInternal(ex, "Wrong request input", new HttpHeaders(),
                HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({NoSuchElementException.class})
    protected ResponseEntity<Object> handleNotFound(Exception ex, WebRequest request) {
        this.makeLog(ex);
        return handleExceptionInternal(ex, "Nothing was found", new HttpHeaders(),
                HttpStatus.NO_CONTENT, request);
    }

    @ExceptionHandler({OptimisticLockException.class})
    protected ResponseEntity<Object> handleOptimisticLock(Exception ex, WebRequest request) {
        this.makeLog(ex);
        return handleExceptionInternal(ex, "Data expired, access forbidden", new HttpHeaders(),
                HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleInternalServerError(Exception ex, WebRequest request) {
        this.makeLog(ex);
        return handleExceptionInternal(ex, "Something went wrong", new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR, request);
    }


    //    @Override
//    protected @NonNull ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//                                                                           @NonNull HttpHeaders headers,
//                                                                           @NonNull HttpStatus status,
//                                                                           @NonNull WebRequest request) {
//        Map<String, List<String>> body = new HashMap<>();
//        List<String> errors = ex.getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .map(DefaultMessageSourceResolvable::getDefaultMessage)
//                .collect(Collectors.toList());
//        body.put("errors", errors);
//        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
//    }
    private void makeLog(Exception ex) {
        logger.error(ex.getMessage() + "\t" + ex.getCause());
    }
}

