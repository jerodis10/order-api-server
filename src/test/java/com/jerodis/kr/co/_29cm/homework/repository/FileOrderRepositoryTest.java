package com.jerodis.kr.co._29cm.homework.repository;

import com.jerodis.kr.co._29cm.homework.domain.Item;
import com.jerodis.kr.co._29cm.homework.domain.Stock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class FileOrderRepositoryTest {

    private final OrderRepository orderRepository = new FileOrderRepository(new HashMap<>());

    @TestFactory
    @DisplayName("저장된 상품들의 가격이 0 이상인지 확인")
    Stream<DynamicTest> success_isPriceMoreZero() {
        List<Stock> stocks = orderRepository.findAllItem();

        return stocks.stream()
            .map(item -> DynamicTest.dynamicTest("상품 [ " + item.getItemNo() + " ] 의 가격이 [ " + item.getPrice() + " ] 0 이상인지 검사",
                () -> {
                    assertThat(isMoreZero(item.getPrice())).isTrue();
                }
        ));
    }

    @TestFactory
    @DisplayName("저장된 상품들의 재고수량이 0 이상인지 확인")
    Stream<DynamicTest> success_isStockMoreZero() {
        List<Stock> stocks = orderRepository.findAllItem();

        return stocks.stream()
            .map(item -> DynamicTest.dynamicTest("상품 [ " + item.getItemNo() + " ] 의 재고수량이 [ " + item.getStockQuantity() + " ] 0 이상인지 검사",
                () -> {
                    assertThat(isMoreZero(item.getStockQuantity())).isTrue();
                }
            ));
    }

    private boolean isMoreZero(Long num) {
        if(num >= 0L) return true;
        else return false;
    }

}