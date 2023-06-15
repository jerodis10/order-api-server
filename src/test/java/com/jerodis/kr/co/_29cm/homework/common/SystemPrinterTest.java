package com.jerodis.kr.co._29cm.homework.common;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

class SystemPrinterTest {

    private static ByteArrayOutputStream outputMessage;
    private final Printer printer = new SystemPrinter();

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
    @DisplayName("System.out.print 콘솔 메시지 확인")
    void print_withSystemOutPrint() {
        String s = "test";
        printer.print(s);
        assertThat(outputMessage.toString()).hasToString(s);
    }

    @Test
    @DisplayName("System.out.println 콘솔 메시지 확인")
    void println_withSystemOutPrint() {
        String s = "test";
        printer.println(s);
        assertThat(outputMessage.toString().trim()).hasToString(s);
    }

    @Test
    @DisplayName("initInput 콘솔 메시지 확인")
    void printInitInput_withSystemOutPrint() {
        printer.initInput();
        assertThat(outputMessage.toString().trim()).hasToString("입력(o[order]: 주문, 종료[quit]: 종료) :");
    }

}