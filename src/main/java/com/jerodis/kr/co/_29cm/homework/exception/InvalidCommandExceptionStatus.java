package com.jerodis.kr.co._29cm.homework.exception;

import lombok.Getter;

@Getter
public enum InvalidCommandExceptionStatus {
    INVALID_COMMAND("ICE_1000", "입력한 명령어가 적절하지 않습니다."),
    INVALID_ITEM_NO("ICE_1100", "입력한 상품번호가 적절하지 않습니다."),
    INVALID_QUANTITY("ICE_1200", "입력한 수량 적절하지 않습니다."),
    NEGATIVE_QUANTITY("ICE_1300", "수량은 1 이상이어야 합니다.."),
    ITEM_NOT_FOUND("ICE_2000", "해당 상품이 존재하지 않습니다.");


    private final String statusCode;
    private final String message;

    InvalidCommandExceptionStatus(String statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
