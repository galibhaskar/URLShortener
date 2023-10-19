package com.galibhaskar.URL_Shortner.exceptionHandlers;

import com.galibhaskar.URL_Shortner.models.ErrorInfo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorInfo> handleExceptions(HttpServletRequest request, Exception ex) {
        System.out.println("Reached Global Exception Handler");

        ResponseEntity<ErrorInfo> obj = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorInfo(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), ex));

        return obj;
    }
}
