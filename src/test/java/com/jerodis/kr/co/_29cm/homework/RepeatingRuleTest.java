package com.jerodis.kr.co._29cm.homework;

import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Concurrent;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;
import com.jerodis.kr.co._29cm.homework.common.InputReader;
import com.jerodis.kr.co._29cm.homework.common.Printer;
import com.jerodis.kr.co._29cm.homework.common.SystemPrinter;
import com.jerodis.kr.co._29cm.homework.domain.Item;
import com.jerodis.kr.co._29cm.homework.domain.OrderDetail;
import com.jerodis.kr.co._29cm.homework.repository.FileOrderRepository;
import com.jerodis.kr.co._29cm.homework.repository.OrderRepository;
import com.jerodis.kr.co._29cm.homework.service.OrderService;
import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class RepeatingRuleTest {
    @Rule
    public RepeatingRule rule = new RepeatingRule();

    private static int counter = 0;

    @Test
    @Concurrent(count = 10)
    @Repeating(repetition = 10)
    public void annotatedTest() {
        counter++;
    }

    @AfterClass
    public static void annotatedTestRunsMultipleTimes2() {
//        assertThat(counter).isEqualTo(100);
        assertThat(counter).isEqualTo(9);
    }

//    private final OrderRepository orderRepository = new FileOrderRepository();
//    private final Printer printer = new SystemPrinter();
//    private final InputReader inputReader = new InputReader();
//    private final OrderService orderService = new OrderService(orderRepository, printer, inputReader);
//    static Map<String, OrderDetail> orderMap = new ConcurrentHashMap<>();
//
//    @Test
//    @Concurrent(count = 10)
//    @Repeating(repetition = 10)
//    public void runsMultipleTimes() {
//        String inputItemNo = "1";
//        Long price = 100_000L;
//        Long inputItemQuantity = 1L;
//        Long baseStock = 100L;
//        Long sumQuantity = inputItemQuantity * 100;
//        Item item = Item.builder()
//                .itemNo(Long.valueOf(inputItemNo))
//                .itemName("pants")
//                .price(price)
//                .quantity(inputItemQuantity)
//                .stock(baseStock)
//                .build();
//
//        orderService.orderProcess(item, inputItemNo, inputItemQuantity, orderMap);
//        System.out.println(orderMap);
//    }
//
//    @AfterClass
//    public static void annotatedTestRunsMultipleTimes() throws InterruptedException {
//
////        System.out.println("a");
////        assertThat(orderMap.get("1").getItem().getQuantity()).isEqualTo(10L);
//        assertThat(orderMap.get("1").getItem().getStock()).isEqualTo(0L);
//    }

}
