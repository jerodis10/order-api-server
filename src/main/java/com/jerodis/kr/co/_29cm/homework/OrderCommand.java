package com.jerodis.kr.co._29cm.homework;

//import com.example.demo.exception.InvalidCommandException;

import com.jerodis.kr.co._29cm.homework.exception.InvalidCommandException;

public enum OrderCommand {
    ORDER("order", "o", "주문"),
    QUIT("quit", "q", "종료")
    ;

    public String commandString;

    public String commandChar;

    public String commandWord;

    OrderCommand(String commandString, String commandChar, String commandWord) {
        this.commandString = commandString;
        this.commandChar = commandChar;
        this.commandWord = commandWord;
    }

    public static OrderCommand of(String s) {
        if (s.equals("o")) return ORDER;
        if (s.equals("q")) return QUIT;
        throw new InvalidCommandException(String.format("[%s] 는 잘못된 명령어입니다.", s));
    }

}