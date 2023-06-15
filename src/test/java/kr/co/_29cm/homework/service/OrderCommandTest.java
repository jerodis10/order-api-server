package kr.co._29cm.homework.service;

import kr.co._29cm.homework.common.OrderCommand;
import kr.co._29cm.homework.exception.InvalidCommandException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderCommandTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"a", "a1", "aa12xd", "12asf", "er31", "qa", "qo"})
    @DisplayName("주문 요청 명령어가 q or o 이외의 값이 입력되면 예외 발생")
    void throwException_whenOrderCommand(String command) {
        // when then
        assertThatThrownBy(() -> OrderCommand.of(command)).isInstanceOf(InvalidCommandException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"q", "o"})
    @DisplayName("주문 요청 명령어는 q or o 만 입력 가능")
    void success_whenOrderCommand(String command) {
        // when then
        assertThat(OrderCommand.of(command)).isInstanceOf(OrderCommand.class);
    }
}