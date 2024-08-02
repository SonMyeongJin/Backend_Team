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
     * 3000 : exercise 관련
     */
    CANNOT_FOUND_EXERCISE(3000, HttpStatus.BAD_REQUEST.value(), "운동을 찾을 수 없습니다."),

    /**
     * 4000 : activityRegion 관련
     */
    CANNOT_FOUND_ACTIVITYREGION(4000, HttpStatus.BAD_REQUEST.value(), "활동지역을 찾을 수 없습니다."),

    /**
     * 5000 : facility 관련
     */
    CANOOT_FOUND_FACILITY(5000, HttpStatus.BAD_REQUEST.value(), "시설을 찾을 수 없습니다."),

    /**
     * 6000: member 관련
     */
    CANNOT_FOUND_MEMBER(6000, HttpStatus.BAD_REQUEST.value(), "유저를 찾을 수 없습니다."),
    ALREADY_EXIST_EMAIL(6001, HttpStatus.BAD_REQUEST.value(), "이미 존재하는 이메일입니다."),

    /**
     * 7000 : crew 관련
     */
    CANNOT_FOUND_CREW_LIST(7000, HttpStatus.BAD_REQUEST.value(), "크루 리스트를 찾을 수 없습니다."),
    CANNOT_FOUND_CREW(7001, HttpStatus.BAD_REQUEST.value(), "크루를 찾을 수 없습니다."),
    ALREADY_FULL_CREW(7002, HttpStatus.BAD_REQUEST.value(), "더이상 크루에 참여하실 수 없습니다."),

    /**
     * 8000 : Member crew 관련
     */
    CANNOT_FOUND_MEMBERCREW(8000, HttpStatus.BAD_REQUEST.value(), "멤버_크루를 찾을 수 없습니다."),
    CANNOT_FOUND_MEMBERCREW_LIST(7001, HttpStatus.BAD_REQUEST.value(), "멤버_크루 리스트를 찾을 수 없습니다.");


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
