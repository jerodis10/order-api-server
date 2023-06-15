package kr.co._29cm.homework.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class NumberUtilTest {

    @Test
    @DisplayName("Long 타입 숫자 포맷 적용 테스트")
    void numberFormatter() {
        // given
        String num1 = NumberUtil.numberFormatter(10L);
        String num2 = NumberUtil.numberFormatter(1_000L);
        String num3 = NumberUtil.numberFormatter(100_000L);
        String num4 = NumberUtil.numberFormatter(10_000_000L);

        assertThat(num1).isEqualTo("10");
        assertThat(num2).isEqualTo("1,000");
        assertThat(num3).isEqualTo("100,000");
        assertThat(num4).isEqualTo("10,000,000");
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "1000", "1000000", "1,000", "10,000,000"})
    @DisplayName("Long 타입 유효성 테스트")
    void success_whenRightNum(String s) {
        assertThat(NumberUtil.isNumeric(s)).isTrue();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"a", "1a", "1a1", "1 a", "1  ", "ewr34", "9223372036854775807000"})
    @DisplayName("Long 타입으로 변경할 수 없는 값 입력시 예외 발생")
    void throwException_whenWrongNum(String s) {
        assertThat(NumberUtil.isNumeric(s)).isFalse();
    }
}