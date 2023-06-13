package com.jerodis.kr.co._29cm.homework.exception;

public class InvalidCommandException extends CommonException{
    public InvalidCommandException(InvalidCommandExceptionStatus invalidCommandExceptionStatus) {
        super(invalidCommandExceptionStatus.getStatusCode(), invalidCommandExceptionStatus.getMessage());
    }

    public InvalidCommandException(InvalidCommandExceptionStatus invalidCommandExceptionStatus, String detailMessage) {
        super(invalidCommandExceptionStatus.getStatusCode(), invalidCommandExceptionStatus.getMessage(), detailMessage);
    }
}
