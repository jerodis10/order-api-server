package kr.co._29cm.homework.domain;

import kr.co._29cm.homework.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderTest extends BaseTest {

    @Test
    @DisplayName("order 생성 테스트 / with 배송료")
    void success_makeOrderWithDeliveryFee() {
        // given
        Long price = 10L;
        Long orderQuantity = 1L;

        Stock stock = Stock.builder()
                .itemNo(123L)
                .itemName("item")
                .price(price)
                .build();

        OrderDetail orderDetail = new OrderDetail(stock, orderQuantity);
        OrderDetail orderDetail2 = new OrderDetail(stock, orderQuantity);
        List<OrderDetail> orderDetails = List.of(orderDetail, orderDetail2);

        // when
        Order order = new Order(orderDetails);

        // then
        assertThat(order.getOrderAmount()).isEqualTo(price * orderQuantity * 2);
        assertThat(order.getPayment()).isEqualTo(price * orderQuantity * 2 + SHIPPING_AMOUNT);
        assertThat(order.getOrderDetails()).hasSize(2);

        assertThat(order.getTotalAmount()).isEqualTo(price * orderQuantity * 2);
        assertThat(order.getDeliveryFee()).isEqualTo(SHIPPING_AMOUNT);
    }

    @Test
    @DisplayName("order 생성 테스트 / no 배송료")
    void success_makeOrderNoDeliveryFee() {
        // given
        Long price = 25_000L;
        Long orderQuantity = 10L;

        Stock stock = Stock.builder()
                .itemNo(123L)
                .itemName("item")
                .price(price)
                .build();

        OrderDetail orderDetail = new OrderDetail(stock, orderQuantity);
        OrderDetail orderDetail2 = new OrderDetail(stock, orderQuantity);
        List<OrderDetail> orderDetails = List.of(orderDetail, orderDetail2);

        // when
        Order order = new Order(orderDetails);

        // then
        assertThat(order.getOrderAmount()).isEqualTo(price * orderQuantity * 2);
        assertThat(order.getPayment()).isEqualTo(price * orderQuantity * 2);
        assertThat(order.getOrderDetails()).hasSize(2);

        assertThat(order.getTotalAmount()).isEqualTo(price * orderQuantity * 2);
        assertThat(order.getDeliveryFee()).isEqualTo(BASIC_SHIPPING_AMOUNT);
    }

}