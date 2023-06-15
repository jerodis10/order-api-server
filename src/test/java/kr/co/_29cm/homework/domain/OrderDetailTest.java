package kr.co._29cm.homework.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderDetailTest {

    Long itemNo;
    String itemName;
    Long price;
    Long orderQuantity;
    Stock stock;

    @BeforeEach
    void setup() {
        itemNo = 123L;
        itemName = "item";
        price = 10L;
        orderQuantity = 1L;

        stock = Stock.builder()
                .itemNo(itemNo)
                .itemName(itemName)
                .price(price)
                .build();
    }

    @Test
    @DisplayName("orderDetail 생성 테스트")
    void success_makeOrderDetail() {
        // given when
        OrderDetail orderDetail = new OrderDetail(stock, orderQuantity);

        // then
        assertThat(orderDetail.getItem().getItemNo()).isEqualTo(itemNo);
        assertThat(orderDetail.getItem().getItemName()).isEqualTo(itemName);
        assertThat(orderDetail.getItem().getPrice()).isEqualTo(price);
        assertThat(orderDetail.getOrderQuantity()).isEqualTo(orderQuantity);
        assertThat(orderDetail.getAmount()).isEqualTo(price * orderQuantity);
    }

    @Test
    @DisplayName("orderDetail 상품 수량 증가 테스트")
    void success_increaseQuantity() {
        // given
        Long increaseQuantity = 1L;

        // when
        OrderDetail orderDetail = new OrderDetail(stock, orderQuantity);
        orderDetail.increaseQuantity(increaseQuantity);

        // then
        assertThat(orderDetail.getOrderQuantity()).isEqualTo(orderQuantity + increaseQuantity);
    }

    @Test
    @DisplayName("orderDetail toString 테스트")
    void success_orderDetailToString() {
        // given when
        OrderDetail orderDetail = new OrderDetail(stock, orderQuantity);

        // then
        assertThat(orderDetail).hasToString(String.format("%s - %d개", itemName, orderQuantity));
    }

}