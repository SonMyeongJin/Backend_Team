package com.example.likelion12.common.exception;

import com.example.likelion12.common.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class CrewException extends RuntimeException{
    private final ResponseStatus exceptionStatus;

    public CrewException(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }

    public CrewException(ResponseStatus exceptionStatus, String message) {
        super(message);
        this.exceptionStatus = exceptionStatus;
    }
}
