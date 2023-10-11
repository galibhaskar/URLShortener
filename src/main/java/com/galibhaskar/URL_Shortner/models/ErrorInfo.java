package com.galibhaskar.URL_Shortner.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorInfo {

    private HttpStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timeStamp;

    private String message;


    private String debugMessage;


    private ErrorInfo() {
        timeStamp = LocalDateTime.now();
    }

    public ErrorInfo(HttpStatus status) {
        this();
        this.status = status;
    }

    public ErrorInfo(HttpStatus status, Throwable ex) {
        this();

        this.status = status;
        this.message = "unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }


    public ErrorInfo(HttpStatus status, String message, Throwable ex) {
        this();

        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }

}
