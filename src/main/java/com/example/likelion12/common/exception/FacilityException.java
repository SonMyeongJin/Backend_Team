package com.example.likelion12.common.exception;

import com.example.likelion12.common.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class FacilityException extends RuntimeException {

    private final ResponseStatus exceptionStatus;

    public FacilityException(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }

    public FacilityException(ResponseStatus exceptionStatus, String message) {
        super(message);
        this.exceptionStatus = exceptionStatus;
    }
}
