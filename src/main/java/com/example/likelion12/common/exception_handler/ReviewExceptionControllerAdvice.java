package com.example.likelion12.common.exception_handler;

import com.example.likelion12.common.exception.MemberCrewException;
import com.example.likelion12.common.exception.ReviewException;
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
public class ReviewExceptionControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ReviewException.class)
    public BaseErrorResponse handle_ReviewException(ReviewException e) {
        log.error("[handle_ReviewException]", e);
        return new BaseErrorResponse(e.getExceptionStatus(), e.getMessage());
    }
}
