package com.jerodis.kr.co._29cm.homework.service;

import com.jerodis.kr.co._29cm.homework.exception.InvalidCommandException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderCommandValidatorTest {

    @ParameterizedTest
    @ValueSource(strings = {"a", "a1", "aa12xd", "12asf", "er31", "qo", "9223372036854775807000"})
    @DisplayName("적절하지 않은 상품번호 입력 시 예외 발생")
    void throwException_validateItemNo(String InputItemNo) {
        assertThatThrownBy(() -> OrderCommandValidator.validateItemNo(InputItemNo)).isInstanceOf(InvalidCommandException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "a1", "aa12xd", "12asf", "er31", "qo", "0", "-1", "-111", "9223372036854775807000"})
    @DisplayName("적절하지 않은 수량 입력 시 예외 발생")
    void throwException_validateQuantity(String InputQuantity) {
        assertThatThrownBy(() -> OrderCommandValidator.validateQuantity(InputQuantity)).isInstanceOf(InvalidCommandException.class);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("상품번호에 빈 문자열 입력시 주문 프로그램 종료")
    void success_whenNullInputItemNo(String inputItemNo) {
        assertThat(OrderCommandValidator.validateItemNo(inputItemNo)).isFalse();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("수량에 빈 문자열 입력시 주문 프로그램 종료")
    void success_whenNullInputQuantity(String inputQuantity) {
        assertThat(OrderCommandValidator.validateQuantity(inputQuantity)).isFalse();
    }
    
}