package com.myschool.kmhss.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CustomException extends Exception {
    public final HttpStatus status;
    public final String message;

    public CustomException(HttpStatus status, String message) {
        this.message = message;
        this.status = status;
    }
}
