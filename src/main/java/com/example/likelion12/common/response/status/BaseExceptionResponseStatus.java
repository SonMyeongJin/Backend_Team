package com.example.likelion12.common.response.status;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum BaseExceptionResponseStatus implements ResponseStatus{

    /**
     * 1000: 요청 성공 (OK)
     */
    SUCCESS(1000,HttpStatus.OK.value(), "요청에 성공하였습니다."),
    FAILURE(2000, HttpStatus.BAD_REQUEST.value(), "요청에 실패하였습니다."),

    /**
     * 3000 : member 관련
     */
    ALREADY_EXIST_EMAIL(3000, HttpStatus.BAD_REQUEST.value(), "이미 존재하는 이메일입니다."),

    /**
     * 4000 : exercise 관련
     */
    CANNOT_FOUND_EXERCISE(4000, HttpStatus.BAD_REQUEST.value(), "조건에 맞는 exercise 객체를 찾을 수 없습니다.");


    private final int code;
    private final int status;
    private final String message;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
