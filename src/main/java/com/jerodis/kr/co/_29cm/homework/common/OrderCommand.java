package com.jerodis.kr.co._29cm.homework.common;


import com.jerodis.kr.co._29cm.homework.exception.InvalidCommandException;
import com.jerodis.kr.co._29cm.homework.exception.InvalidCommandExceptionStatus;
import lombok.Getter;

@Getter
public enum OrderCommand {
    ORDER("order", "o", "주문"),
    QUIT("quit", "q", "종료")
    ;

    private final String commandString;

    private final String commandChar;

    private final String commandWord;

    OrderCommand(String commandString, String commandChar, String commandWord) {
        this.commandString = commandString;
        this.commandChar = commandChar;
        this.commandWord = commandWord;
    }

    public static OrderCommand of(String s) {
        if (OrderCommand.ORDER.commandChar.equals(s) || OrderCommand.ORDER.commandString.equals(s)) return ORDER;
        if (OrderCommand.QUIT.commandChar.equals(s) || OrderCommand.QUIT.commandString.equals(s)) return QUIT;
        throw new InvalidCommandException(InvalidCommandExceptionStatus.INVALID_COMMAND, s);
    }

}
