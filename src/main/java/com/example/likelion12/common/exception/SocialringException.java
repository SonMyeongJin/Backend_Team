package com.example.likelion12.common.exception;

import com.example.likelion12.common.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class SocialringException extends RuntimeException{
    private final ResponseStatus exceptionStatus;

    public SocialringException(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }

    public SocialringException(ResponseStatus exceptionStatus, String message) {
        super(message);
        this.exceptionStatus = exceptionStatus;
    }
}
