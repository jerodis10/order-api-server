package com.jerodis.kr.co._29cm.homework.service;

import com.jerodis.kr.co._29cm.homework.exception.InvalidCommandException;
import com.jerodis.kr.co._29cm.homework.exception.InvalidCommandExceptionStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class OrderCommandValidatorTest {

    @ParameterizedTest
    @ValueSource(strings = {"a", "a1", "aa12xd", "qo"})
    @DisplayName("적절하지 않은 상품번호 입력 시 예외 발생")
    void throwException_validateItemNo(String InputItemNo) {
        assertThatThrownBy(() -> OrderCommandValidator.validateItemNo(InputItemNo)).isInstanceOf(InvalidCommandException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "a11", "aa12xd", "qo", "0", "-1", "-111"})
    @DisplayName("적절하지 않은 수량 입력 시 예외 발생")
    void throwException_validateQuantity(String InputQuantity) {
        assertThatThrownBy(() -> OrderCommandValidator.validateQuantity(InputQuantity)).isInstanceOf(InvalidCommandException.class);
    }

    @DisplayName("상품번호에 빈 문자열 입력시 주문 프로그램 종료")
    @Test
    void success_whenNullInputItemNo() {
        assertThat(OrderCommandValidator.validateItemNo("")).isFalse();
    }

    @DisplayName("수량에 빈 문자열 입력시 주문 프로그램 종료")
    @Test
    void success_whenNullInputQuantity() {
        assertThat(OrderCommandValidator.validateQuantity("")).isFalse();
    }
}