package com.jerodis.kr.co._29cm.homework.domain;

import com.jerodis.kr.co._29cm.homework.service.OrderCommandValidator;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.BuilderArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Or;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ItemTest {
//    private static final FixtureMonkey fixtureMonkey = FixtureMonkey.builder().build();

    private static final FixtureMonkey sut = FixtureMonkey.builder()
            .objectIntrospector(BuilderArbitraryIntrospector.INSTANCE)
//            .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
            .build();
//            .defaultGenerator(FieldReflectionArbitraryGenerator.INSTANCE)
//            .nullInject(0)
//            .build();

    @Test
    void test() {
//        FixtureMonkey sut = FixtureMonkey.create();
        Ord ord = sut.giveMeBuilder(Ord.class).set("id", 1L).sample();
//        Ord ord = sut.giveMeOne(Ord.class);

//        List<Ord> ord = sut.giveMeBuilder(Ord.class).set("id", 1L).sampleList(2);

        assertThat(ord.getId()).isEqualTo(1L);
    }

    @Test
    void test2() {
        FixtureMonkey sut = FixtureMonkey.create();
        Order order = sut.giveMeBuilder(Order.class).set("orderAmount", 1L).sample();
//        Ord ord = sut.giveMeOne(Ord.class);

        assertThat(order).isNotNull();
    }

    @Test
    void test3() {
//        FixtureMonkey sut = FixtureMonkey.create();
        Order order = sut.giveMeBuilder(Order.class).set("orderAmount", 1L).sample();
//        Ord ord = sut.giveMeOne(Ord.class);

        assertThat(order).isNotNull();
    }

    @DisplayName("상품번호에 빈 문자열 입력시 주문 프로그램 종료")
    @Test
    void success_whenNullInputItemNo() {
        Item item = sut.giveMeBuilder(Item.class)
                .set("itemNo", 1L)
                .sample();

//        Item item = FixtureMonkey.create().giveMeOne(Item.class);
//        Item item = FixtureMonkey.create().giveMeBuilder(Item.class)
//                .set("itemNo", 1L)
//                .sample();


        System.out.println(item);

//        Item item = new Item();

//        String inputItemNo = "1";
//        Long price = 2L;
//        Long inputItemQuantity = 0L;
//        Long baseStock = 10L;
//        Item item = Item.builder()
//                .itemNo(Long.valueOf(inputItemNo))
//                .itemName("pants")
//                .price(price)
//                .quantity(inputItemQuantity)
//                .stock(baseStock)
//                .build();
//
//        assertThat(OrderCommandValidator.validateItemNo("")).isFalse();
    }

}