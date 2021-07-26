package com.tasks.test.user.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.util.List;

/**
 * Описание ошибки
 */
@Setter
@Getter
public class ErrorResponse
{
    private String timestamp;
    private HttpStatus status;
    private String message;
    private List<String> errors;

    public ErrorResponse(String message, List<String> errors, HttpStatus status)
    {
        this.timestamp = new Timestamp(System.currentTimeMillis()).toString();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ErrorResponse(String message, HttpStatus status)
    {
        this.timestamp = new Timestamp(System.currentTimeMillis()).toString();
        this.status = status;
        this.message = message;
    }
}
