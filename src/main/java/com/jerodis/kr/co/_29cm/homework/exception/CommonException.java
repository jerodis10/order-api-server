package com.jerodis.kr.co._29cm.homework.exception;

import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {
    private final String errorCode;
    private final String errorMessage;

    public CommonException(final String errorCode, final String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public CommonException(final String errorCode, final String errorMessage, final String detailMessage) {
        super(detailMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public CommonException(CommonExceptionStatus commonExceptionStatus) {
        super(commonExceptionStatus.getMessage());
        this.errorCode = commonExceptionStatus.getCode();
        this.errorMessage = commonExceptionStatus.getMessage();
    }

    public CommonException(CommonExceptionStatus commonExceptionStatus, final String detailMessage) {
        super(detailMessage);
        this.errorCode = commonExceptionStatus.getCode();
        this.errorMessage = commonExceptionStatus.getMessage();
    }
}
