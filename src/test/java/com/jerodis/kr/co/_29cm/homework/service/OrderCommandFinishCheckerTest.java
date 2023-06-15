package com.jerodis.kr.co._29cm.homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class OrderCommandFinishCheckerTest {

    @Test
    @DisplayName("하나의 공백 입력시 true 반환")
    void true_whenOneWhitespace() {
        assertThat(OrderCommandFinishChecker.isFinish(" ")).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"  ", "    ", " 12", "12  ", "1  1", " 1  1", " 1  1 ", "1"})
    @DisplayName("하나의 공백이 입력되지 않은 경우 false 반환")
    void false_whenNotOneWhitespace(String s) {
        assertThat(OrderCommandFinishChecker.isFinish(s)).isFalse();
    }
}