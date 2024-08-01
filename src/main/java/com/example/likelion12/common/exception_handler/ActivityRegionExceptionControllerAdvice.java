package com.example.likelion12.common.exception_handler;

import com.example.likelion12.common.exception.ActivityRegionException;
import com.example.likelion12.common.response.BaseErrorResponse;
import jakarta.annotation.Priority;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Priority(0)
@RestControllerAdvice
public class ActivityRegionExceptionControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ActivityRegionException.class)
    public BaseErrorResponse handle_ActivityRegionException(ActivityRegionException e) {
        log.error("[handle_ActivityRegionException]", e);
        return new BaseErrorResponse(e.getExceptionStatus(), e.getMessage());
    }
}
