package com.jerodis.kr.co._29cm.homework.exception;

import lombok.Getter;

@Getter
public enum CommonExceptionStatus {
    UNEXPECTED("COMMON_0000", "요청을 처리하지 못했습니다."),
    WRONG_ARGUMENT("COMMON_1000", "전달받은 매개변수가 올바르지 않습니다."),
    IO_EXCEPTION("COMMON_2000", "입출력 처리 에러가 발생했습니다."),
    INTERNAL_SERVER_ERROR("COMMON_3000", "Internal server error");

    private final String code;
    private final String message;

    CommonExceptionStatus(final String code, final String message) {
        this.code = code;
        this.message = message;
    }
}
