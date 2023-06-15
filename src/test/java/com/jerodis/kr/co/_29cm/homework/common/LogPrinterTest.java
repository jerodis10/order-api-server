package com.jerodis.kr.co._29cm.homework.common;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

class LogPrinterTest {
    private static ByteArrayOutputStream outputMessage;
    private final Printer printer = new LogPrinter();

    @BeforeEach
    void setUpStreams() {
        outputMessage = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputMessage));
    }

    @AfterEach
    void restoresStreams() {
        System.setOut(System.out);
    }

    @Test
    @DisplayName("log print 콘솔 메시지 확인")
    void print_withLogPrint() {
        String s = "test";
        printer.print(s);
        assertThat(outputMessage.toString().trim()).hasToString(s);
    }

    @Test
    @DisplayName("log println 콘솔 메시지 확인")
    void println_withLogPrint() {
        String s = "test";
        printer.println(s);
        assertThat(outputMessage.toString().trim()).hasToString(s);
    }

    @Test
    @DisplayName("log initInput 콘솔 메시지 확인")
    void printInitInput_withLogPrint() {
        printer.initInput();
        assertThat(outputMessage.toString().trim()).hasToString("입력(o[order]: 주문, 종료[quit]: 종료) :");
    }
}