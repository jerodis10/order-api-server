package kr.co._29cm.homework.event;

import kr.co._29cm.homework.common.OrderCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

class OrderEventPublisherTest {

    private final OrderEventListener orderEventListener = new OrderEventListener();
    private final OrderEventPublisher orderEventPublisher = new OrderEventPublisher(orderEventListener);

    private static ByteArrayOutputStream outputMessage;

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
    @DisplayName("주문 종료 콘솔 메시지 확인")
    void print_whenOrderFinished() {
        orderEventPublisher.publishEvent(OrderCommand.QUIT);
        assertThat(outputMessage.toString().trim()).hasToString("고객님의 주문에 감사드립니다.");
    }

}