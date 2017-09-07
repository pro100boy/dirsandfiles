package com.test.dirsandfiles.util;

import javassist.NotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;
import java.nio.file.AccessDeniedException;
import java.sql.SQLException;

@ControllerAdvice(annotations = Controller.class)
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {AccessDeniedException.class, NotFoundException.class})
    protected ResponseEntity<Object> accessDenied(RuntimeException ex, WebRequest request) {
        String rootMsg = getRootCause(ex).getMessage();
        return handleExceptionInternal(ex, rootMsg,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({ValidationException.class})
    protected ResponseEntity<Object> handleArgumentException(RuntimeException ex, WebRequest request) {
        String rootMsg = getRootCause(ex).getMessage();
        return handleExceptionInternal(ex, rootMsg,
                new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    @ExceptionHandler(value = {SQLException.class, DataAccessException.class, TransactionException.class})
    protected ResponseEntity<Object> handleDBException(RuntimeException ex, WebRequest request) {
        String rootMsg = getRootCause(ex).getMessage();
        return handleExceptionInternal(ex, rootMsg,
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    public static Throwable getRootCause(Throwable t) {
        Throwable result = t;
        Throwable cause;

        while (null != (cause = result.getCause()) && (result != cause)) {
            result = cause;
        }
        return result;
    }
}
