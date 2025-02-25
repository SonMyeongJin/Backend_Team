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
     * 7000: socialring 관련
     */
    CANNOT_FOUND_SOCIALRING(7000, HttpStatus.BAD_REQUEST.value(), "소셜링을 찾을 수 없습니다."),
    ALREADY_FULL_SOCIALRING(7001, HttpStatus.BAD_REQUEST.value(), "더이상 소셜링에 참여 할 수 없습니다."),


    /**
     * 8000: member socialring 관련
     */
    CANNOT_FOUND_MEMBERSOCIALRING(8000, HttpStatus.BAD_REQUEST.value(), "해당하는 멤버소셜링을 찾을 수 없습니다."),
    NOT_MEMBERSOCIALRING_CAPTAIN(8001, HttpStatus.BAD_REQUEST.value(), "소셜링 수정,삭제에 접근할수없는 권한입니다."),
    CANNOT_FOUND_MEMBERSOCIALRING_LIST(8002, HttpStatus.BAD_REQUEST.value(), "해당하는 멤버소셜링 리스트를 찾을 수 없습니다."),
    ALREADY_EXIST_IN_SOCIALRING(8003, HttpStatus.BAD_REQUEST.value(), "해당 소셜링에 이미 등록된 멤버입니다."),
    N0T_EXIST_JOIN_SOCIALRING(8004, HttpStatus.BAD_REQUEST.value(), "참가 중인 소셜링이 존재하지 않습니다."),
    N0T_EXIST_JOIN_BEFORE_SOCIALRING(8005, HttpStatus.BAD_REQUEST.value(), "참가 예정인 소셜링이 존재 하지않습니다."),
    N0T_EXIST_JOIN_COMPLETE_SOCIALRING(8006, HttpStatus.BAD_REQUEST.value(), "참가 완료한 소셜링이 존재 하지않습니다."),
    CANNOT_CANCEL_BY_CAPTAIN(8007, HttpStatus.BAD_REQUEST.value(), "소셜링 모임장은 소셜링을 나갈 수 없습니다. 소셜링 삭제를 이용해주세요"),

    /**
     * 9000 : crew 관련
     */
    CANNOT_FOUND_CREW_LIST(9000, HttpStatus.BAD_REQUEST.value(), "크루 리스트를 찾을 수 없습니다."),
    CANNOT_FOUND_CREW(9001, HttpStatus.BAD_REQUEST.value(), "크루를 찾을 수 없습니다."),
    ALREADY_FULL_CREW(9002, HttpStatus.BAD_REQUEST.value(), "더이상 크루에 참여하실 수 없습니다."),


    /**
     * 10000 : Member crew 관련
     */
    CANNOT_FOUND_MEMBERCREW(10000, HttpStatus.BAD_REQUEST.value(), "멤버_크루를 찾을 수 없습니다."),
    CANNOT_FOUND_MEMBERCREW_LIST(10001, HttpStatus.BAD_REQUEST.value(), "멤버_크루 리스트를 찾을 수 없습니다."),
    ALREADY_EXIST_IN_CREW(10002, HttpStatus.BAD_REQUEST.value(), "해당 크루에 이미 등록된 멤버입니다."),
    NOT_MEMBERCREW_CAPTAIN(10003, HttpStatus.BAD_REQUEST.value(), "크루 수정,삭제에 접근할수없는 권한입니다."),
    NOT_CREW_MEMBERCREW(10004, HttpStatus.BAD_REQUEST.value(), "해당 크루에 참여 상태가 아닙니다."),
    CANNOT_CREW_CANCEL(10005, HttpStatus.BAD_REQUEST.value(), "해당 크루를 탈퇴 할 수 없습니다. 크루 삭제를 이용해주세요."),

    /**
     * 11000 : Review 관련
     */
    CANNOT_FOUND_REVIEW(11000, HttpStatus.BAD_REQUEST.value(), "리뷰를 찾을 수 없습니다."),
    ALREADY_EXIST_REVIEW(11001, HttpStatus.BAD_REQUEST.value(), "이미 등록한 리뷰가 있습니다. 수정으로 작성해주세요."),
    CANNOT_SET_SCORE(11002, HttpStatus.BAD_REQUEST.value(), "점수가 너무 높거나 낮습니다. 1~5점으로 평가해주세요"); // 추가된 상수


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
