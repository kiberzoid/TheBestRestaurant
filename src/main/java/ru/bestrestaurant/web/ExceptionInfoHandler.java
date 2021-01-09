package ru.bestrestaurant.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.bestrestaurant.util.ValidationUtil;
import ru.bestrestaurant.util.exception.BadParametersException;
import ru.bestrestaurant.util.exception.ErrorInfo;
import ru.bestrestaurant.util.exception.NotAllowedOpException;
import ru.bestrestaurant.util.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionInfoHandler {
    private static final Logger log = LoggerFactory.getLogger(ExceptionInfoHandler.class);

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorInfo> handleError(HttpServletRequest req, NotFoundException e){
        return getErrorInfo(req, e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({BadParametersException.class, NotAllowedOpException.class})
    public ResponseEntity<ErrorInfo> handleWrongRequestError(HttpServletRequest req, Exception e){
        return getErrorInfo(req, e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorInfo> handleError(HttpServletRequest req, DataIntegrityViolationException e){
        return getErrorInfo(req, e, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> handleError(HttpServletRequest req, Exception e){
        return getErrorInfo(req, e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorInfo> getErrorInfo(HttpServletRequest req, Exception e, HttpStatus status) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        ErrorInfo err = new ErrorInfo(req.getRequestURL(), rootCause.getClass().getName(), rootCause.getMessage());
        log.error(err.toString());
        return ResponseEntity.status(status)
                .body(err);
    }
}
