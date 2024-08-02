package com.example.likelion12.common.exception_handler;

import com.example.likelion12.common.exception.FacilityException;
import com.example.likelion12.common.exception.MemberCrewException;
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
public class MemberCrewExceptionControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MemberCrewException.class)
    public BaseErrorResponse handle_MemberCrewException(MemberCrewException e) {
        log.error("[handle_MemberCrewException]", e);
        return new BaseErrorResponse(e.getExceptionStatus(), e.getMessage());
    }
}
