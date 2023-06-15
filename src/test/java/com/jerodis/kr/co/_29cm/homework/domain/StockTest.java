package com.jerodis.kr.co._29cm.homework.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StockTest {

    Long itemNo;
    String itemName;
    Long price;
    Long stockQuantity;
    Stock stock;

    @BeforeEach
    void setup() {
        itemNo = 123L;
        itemName = "item";
        price = 10L;
        stockQuantity = 1L;

        stock = Stock.builder()
                .itemNo(itemNo)
                .itemName(itemName)
                .price(price)
                .stockQuantity(stockQuantity)
                .build();
    }

    @Test
    @DisplayName("stock 생성 테스트")
    void success_decreaseStock() {
        // when then
        assertThat(stock.getItemNo()).isEqualTo(itemNo);
        assertThat(stock.getItemName()).isEqualTo(itemName);
        assertThat(stock.getPrice()).isEqualTo(price);
        assertThat(stock.getStockQuantity()).isEqualTo(stockQuantity);
    }

    @Test
    @DisplayName("stock decrease 테스트")
    void success_makeStock() {
        // given
        Long decreaseQuantity = 1L;

        // when
        stock.decreaseStock(decreaseQuantity);

        // then
        assertThat(stock.getStockQuantity()).isEqualTo(stockQuantity - decreaseQuantity);
    }

    @Test
    @DisplayName("stock toListStock 테스트")
    void success_toListStock() {
        // given
        String itemNo = "768848";
        String itemName = "텀블러";
        String price = "1000";
        String stockQuantity = "10";
        String[] s = new String[]{itemNo, itemName, price, stockQuantity};
        List<String[]> paramList = new ArrayList<>();
        paramList.add(s);

        // when
        List<Stock> stocks = Stock.toListStock(paramList);

        // then
        assertThat(stocks).hasSize(1);
        assertThat(stocks.get(0).getItemNo()).isEqualTo(Long.parseLong(itemNo));
        assertThat(stocks.get(0).getItemName()).isEqualTo(itemName);
        assertThat(stocks.get(0).getPrice()).isEqualTo(Long.parseLong(price));
        assertThat(stocks.get(0).getStockQuantity()).isEqualTo(Long.parseLong(stockQuantity));
    }

}