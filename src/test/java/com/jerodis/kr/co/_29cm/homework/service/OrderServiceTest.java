package com.jerodis.kr.co._29cm.homework.service;

import com.jerodis.kr.co._29cm.homework.common.FileReader;
import com.jerodis.kr.co._29cm.homework.common.InputReader;
import com.jerodis.kr.co._29cm.homework.common.Printer;
import com.jerodis.kr.co._29cm.homework.common.SystemPrinter;
import com.jerodis.kr.co._29cm.homework.domain.Item;
import com.jerodis.kr.co._29cm.homework.domain.Order;
import com.jerodis.kr.co._29cm.homework.domain.OrderDetail;
import com.jerodis.kr.co._29cm.homework.exception.InvalidCommandException;
import com.jerodis.kr.co._29cm.homework.exception.SoldOutException;
import com.jerodis.kr.co._29cm.homework.repository.FileOrderRepository;
import com.jerodis.kr.co._29cm.homework.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderServiceTest {

    private static final Long MINIMUM_SHIPPING_FEE = 50_000L;
    private static final Long BASIC_SHIPPING_FEE = 0L;
    private static final Long SHIPPING_FEE = 2_000L;

    private final OrderRepository orderRepository = new FileOrderRepository();
    private final Printer printer = new SystemPrinter();
    private final InputReader inputReader = new InputReader();
    private final OrderService orderService = new OrderService(orderRepository, printer, inputReader);


    @DisplayName("단일 상품, 단일 주문 요청 / 재고수량 이상 주문시 예외 발생")
    @Test
    void throwException_SingleItem_withSingleOrder_moreStock() {
        // given
        String inputItemNo = "1";
        Long price = 1L;
        Long inputItemQuantity = 20L;
        Long baseStock = 10L;
        Long deliveryFee = price * inputItemQuantity > MINIMUM_SHIPPING_FEE ? BASIC_SHIPPING_FEE : SHIPPING_FEE;
        Item item = Item.builder()
                .itemNo(Long.valueOf(inputItemNo))
                .itemName("pants")
                .price(price)
                .quantity(inputItemQuantity)
                .stock(baseStock)
                .build();

        Map<String, OrderDetail> orderMap = new ConcurrentHashMap<>();

        // when then
        assertThatThrownBy(() -> orderService.orderProcess(item, inputItemNo, inputItemQuantity, orderMap)).isInstanceOf(SoldOutException.class);
    }

    @DisplayName("단일 상품, 복수 주문 요청 / 재고수량 이상 주문시 예외 발생")
    @Test
    void throwException_SingleItem_withMultiOrder_moreStock() {
        // given
        String inputItemNo = "1";
        Long price = 1L;
        Long inputItemQuantity = 6L;
        Long baseStock = 10L;
        Item item = Item.builder()
                .itemNo(Long.valueOf(inputItemNo))
                .itemName("pants")
                .price(price)
                .quantity(inputItemQuantity)
                .stock(baseStock)
                .build();

        Map<String, OrderDetail> orderMap = new ConcurrentHashMap<>();

        // when then
        orderService.orderProcess(item, inputItemNo, inputItemQuantity, orderMap);
        assertThatThrownBy(() -> orderService.orderProcess(item, inputItemNo, inputItemQuantity, orderMap)).isInstanceOf(SoldOutException.class);
    }

    @DisplayName("존재하지 않는 상품 조회 시 예외 발생")
    @Test
    void throwException_whenNotFoundItem() {
        // when then
        assertThatThrownBy(() -> orderRepository.findOneItem("123")).isInstanceOf(InvalidCommandException.class);
    }

    @DisplayName("멀티쓰레드 요청, 상품 재고 부족시 예외 발생")
    @Test
    void throwException_whenNotEnoughStock_withMultiThread() throws InterruptedException {
        // given
        int threadCount = 200;
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        AtomicInteger exceptionCounter = new AtomicInteger(0);
        CountDownLatch latch = new CountDownLatch(threadCount);
        Map<String, OrderDetail> orderMap = new ConcurrentHashMap<>();

        String inputItemNo = "1";
        Long price = 100_000L;
        Long inputItemQuantity = 1L;
        Long baseStock = 100L;
        Long sumQuantity = inputItemQuantity * threadCount;
        Item item = Item.builder()
                .itemNo(Long.valueOf(inputItemNo))
                .itemName("pants")
                .price(price)
                .quantity(inputItemQuantity)
                .stock(baseStock)
                .build();

        // when
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                    try {
                        orderService.orderProcess(item, inputItemNo, inputItemQuantity, orderMap);
                    } catch (SoldOutException e) {
                        exceptionCounter.incrementAndGet();
                    } finally {
                        latch.countDown();
                    }
                }
            );
        }
        latch.await();

        // then
        assertThat(exceptionCounter.get()).isEqualTo(threadCount - baseStock);
    }

    @DisplayName("단일 상품, 단일 주문 요청")
    @Test
    void success_SingleItem_withSingleOrder() {
        // given
        String inputItemNo = "1";
        Long price = 1L;
        Long inputItemQuantity = 2L;
        Long baseStock = 10L;
        Long deliveryFee = price * inputItemQuantity > MINIMUM_SHIPPING_FEE ? BASIC_SHIPPING_FEE : SHIPPING_FEE;
        Item item = Item.builder()
                .itemNo(Long.valueOf(inputItemNo))
                .itemName("pants")
                .price(price)
                .quantity(inputItemQuantity)
                .stock(baseStock)
                .build();

        Map<String, OrderDetail> orderMap = new ConcurrentHashMap<>();

        // when
        orderService.orderProcess(item, inputItemNo, inputItemQuantity, orderMap);
        Order order = new Order(new ArrayList<>(orderMap.values()));

        // then
        assertThat(orderMap.get(inputItemNo).getItem().getQuantity()).isEqualTo(inputItemQuantity);
        assertThat(orderMap.get(inputItemNo).getItem().getStock()).isEqualTo(baseStock - inputItemQuantity);
        assertThat(order.getDeliveryFee()).isEqualTo(deliveryFee);
        assertThat(order.getOrderAmount()).isEqualTo(price * inputItemQuantity + order.getDeliveryFee());
        assertThat(order.getPayment()).isEqualTo(price * inputItemQuantity + order.getDeliveryFee());
    }

    @DisplayName("단일 상품, 복수 주문 요청")
    @Test
    void success_SingleItem_withMultiOrder() {
        // given
        int count = 3;
        String inputItemNo = "1";
        Long price = 1L;
        Long inputItemQuantity = 2L;
        Long baseStock = 10L;
        Item item = Item.builder()
                .itemNo(Long.valueOf(inputItemNo))
                .itemName("pants")
                .price(price)
                .quantity(inputItemQuantity)
                .stock(baseStock)
                .build();

        Long sumCost = price * inputItemQuantity * count;
        Long deliveryFee = sumCost > MINIMUM_SHIPPING_FEE ? BASIC_SHIPPING_FEE : SHIPPING_FEE;

        Map<String, OrderDetail> orderMap = new ConcurrentHashMap<>();

        // when
        for (int i = 0; i < count; i++) {
            orderService.orderProcess(item, inputItemNo, inputItemQuantity, orderMap);
        }
        Order order = new Order(new ArrayList<>(orderMap.values()));

        // then
        assertThat(orderMap.get(inputItemNo).getItem().getQuantity()).isEqualTo(inputItemQuantity * count);
        assertThat(orderMap.get(inputItemNo).getItem().getStock()).isEqualTo(baseStock - (inputItemQuantity * count));
        assertThat(order.getDeliveryFee()).isEqualTo(deliveryFee);
        assertThat(order.getOrderAmount()).isEqualTo(sumCost + order.getDeliveryFee());
        assertThat(order.getPayment()).isEqualTo(sumCost + order.getDeliveryFee());
    }

    @DisplayName("복수 상품, 단일 주문 요청")
    @Test
    void success_MultiItem_withSingleOrder() {
        // given
        String inputItemNo = "1";
        Long price = 1L;
        Long inputItemQuantity = 2L;
        Long baseStock = 10L;
        Item item = Item.builder()
                .itemNo(Long.valueOf(inputItemNo))
                .itemName("pants")
                .price(price)
                .quantity(inputItemQuantity)
                .stock(baseStock)
                .build();

        String inputItemNo2 = "2";
        Long price2 = 2L;
        Long inputItemQuantity2 = 3L;
        Long baseStock2 = 20L;
        Item item2 = Item.builder()
                .itemNo(Long.valueOf(inputItemNo2))
                .itemName("shirts")
                .price(price2)
                .quantity(inputItemQuantity2)
                .stock(baseStock2)
                .build();

        Long sumCost = (price * inputItemQuantity) + (price2 * inputItemQuantity2);
        Long deliveryFee = sumCost > MINIMUM_SHIPPING_FEE ? BASIC_SHIPPING_FEE : SHIPPING_FEE;

        Map<String, OrderDetail> orderMap = new ConcurrentHashMap<>();

        // when
        orderService.orderProcess(item, inputItemNo, inputItemQuantity, orderMap);
        orderService.orderProcess(item2, inputItemNo2, inputItemQuantity2, orderMap);
        Order order = new Order(new ArrayList<>(orderMap.values()));

        // then
        assertThat(orderMap.get(inputItemNo).getItem().getQuantity()).isEqualTo(inputItemQuantity);
        assertThat(orderMap.get(inputItemNo).getItem().getStock()).isEqualTo(baseStock - (inputItemQuantity));

        assertThat(orderMap.get(inputItemNo2).getItem().getQuantity()).isEqualTo(inputItemQuantity2);
        assertThat(orderMap.get(inputItemNo2).getItem().getStock()).isEqualTo(baseStock2 - (inputItemQuantity2));

        assertThat(order.getDeliveryFee()).isEqualTo(deliveryFee);
        assertThat(order.getOrderAmount()).isEqualTo(sumCost + order.getDeliveryFee());
        assertThat(order.getPayment()).isEqualTo(sumCost + order.getDeliveryFee());
    }

    @DisplayName("복수 상품, 복수 주문 요청")
    @Test
    void success_MultiItem_withMultiOrder() {
        // given
        int count = 3;
        String inputItemNo = "1";
        Long price = 1L;
        Long inputItemQuantity = 2L;
        Long baseStock = 10L;
        Item item = Item.builder()
                .itemNo(Long.valueOf(inputItemNo))
                .itemName("pants")
                .price(price)
                .quantity(inputItemQuantity)
                .stock(baseStock)
                .build();

        String inputItemNo2 = "2";
        Long price2 = 2L;
        Long inputItemQuantity2 = 3L;
        Long baseStock2 = 20L;
        Item item2 = Item.builder()
                .itemNo(Long.valueOf(inputItemNo2))
                .itemName("shirts")
                .price(price2)
                .quantity(inputItemQuantity2)
                .stock(baseStock2)
                .build();

        Long sumCost = ((price * inputItemQuantity) + (price2 * inputItemQuantity2)) * count;
        Long deliveryFee = sumCost > MINIMUM_SHIPPING_FEE ? BASIC_SHIPPING_FEE : SHIPPING_FEE;

        Map<String, OrderDetail> orderMap = new ConcurrentHashMap<>();

        // when
        for (int i = 0; i < count; i++) {
            orderService.orderProcess(item, inputItemNo, inputItemQuantity, orderMap);
            orderService.orderProcess(item2, inputItemNo2, inputItemQuantity2, orderMap);
        }
        Order order = new Order(new ArrayList<>(orderMap.values()));

        // then
        assertThat(orderMap.get(inputItemNo).getItem().getQuantity()).isEqualTo(inputItemQuantity * count);
        assertThat(orderMap.get(inputItemNo).getItem().getStock()).isEqualTo(baseStock - (inputItemQuantity  * count));

        assertThat(orderMap.get(inputItemNo2).getItem().getQuantity()).isEqualTo(inputItemQuantity2  * count);
        assertThat(orderMap.get(inputItemNo2).getItem().getStock()).isEqualTo(baseStock2 - (inputItemQuantity2  * count));

        assertThat(order.getDeliveryFee()).isEqualTo(deliveryFee);
        assertThat(order.getOrderAmount()).isEqualTo(sumCost + order.getDeliveryFee());
        assertThat(order.getPayment()).isEqualTo(sumCost + order.getDeliveryFee());
    }

    @DisplayName("복수 상품, 복수 주문 요청, no 배송료")
    @Test
    void success_MultiItem_withMultiOrder_withoutDeliveryFee() {
        // given
        int count = 3;
        String inputItemNo = "1";
        Long price = 100_000L;
        Long inputItemQuantity = 2L;
        Long baseStock = 10L;
        Item item = Item.builder()
                .itemNo(Long.valueOf(inputItemNo))
                .itemName("pants")
                .price(price)
                .quantity(inputItemQuantity)
                .stock(baseStock)
                .build();

        String inputItemNo2 = "2";
        Long price2 = 200_000L;
        Long inputItemQuantity2 = 3L;
        Long baseStock2 = 20L;
        Item item2 = Item.builder()
                .itemNo(Long.valueOf(inputItemNo2))
                .itemName("shirts")
                .price(price2)
                .quantity(inputItemQuantity2)
                .stock(baseStock2)
                .build();

        Long sumCost = ((price * inputItemQuantity) + (price2 * inputItemQuantity2)) * count;
        Long deliveryFee = sumCost > MINIMUM_SHIPPING_FEE ? BASIC_SHIPPING_FEE : SHIPPING_FEE;

        Map<String, OrderDetail> orderMap = new ConcurrentHashMap<>();

        // when
        for (int i = 0; i < count; i++) {
            orderService.orderProcess(item, inputItemNo, inputItemQuantity, orderMap);
            orderService.orderProcess(item2, inputItemNo2, inputItemQuantity2, orderMap);
        }
        Order order = new Order(new ArrayList<>(orderMap.values()));

        // then
        assertThat(orderMap.get(inputItemNo).getItem().getQuantity()).isEqualTo(inputItemQuantity * count);
        assertThat(orderMap.get(inputItemNo).getItem().getStock()).isEqualTo(baseStock - (inputItemQuantity  * count));

        assertThat(orderMap.get(inputItemNo2).getItem().getQuantity()).isEqualTo(inputItemQuantity2  * count);
        assertThat(orderMap.get(inputItemNo2).getItem().getStock()).isEqualTo(baseStock2 - (inputItemQuantity2  * count));

        assertThat(order.getDeliveryFee()).isEqualTo(deliveryFee);
        assertThat(order.getOrderAmount()).isEqualTo(sumCost + order.getDeliveryFee());
        assertThat(order.getPayment()).isEqualTo(sumCost + order.getDeliveryFee());
    }

    @DisplayName("상품 전체 조회")
    @Test
    void success_whenFindAllItem() throws IOException {
        // given
        FileReader fileReader = new FileReader();
        File file = fileReader.read();

        List<String[]> fileList = Files.lines(file.toPath())
                .skip(1)
                .map(line -> line.split(","))
                .toList();

        // when
        List<Item> items = orderService.getItems();

        // then
        assertThat(items.size()).isEqualTo(fileList.size());
    }

    @ParameterizedTest
    @ValueSource(strings = {"648418", "782858", "502480", "778422"})
    @DisplayName("단일 상품 조회")
    void success_whenFindOneItem(String itemNo) throws IOException {
        // given when
        Optional<Item> item = orderRepository.findOneItem(itemNo);

        // then
        assertThat(item).isNotNull();
    }

    @DisplayName("멀티쓰레드 요청 시 상품 재고 이동 테스트")
    @Test
    void success_whenNotEnoughStock_withMultiThread() throws InterruptedException {
        // given
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);
        Map<String, OrderDetail> orderMap = new ConcurrentHashMap<>();

        String inputItemNo = "1";
        Long price = 100_000L;
        Long inputItemQuantity = 1L;
        Long baseStock = 100L;
        Long sumQuantity = inputItemQuantity * threadCount;
        Item item = Item.builder()
                .itemNo(Long.valueOf(inputItemNo))
                .itemName("pants")
                .price(price)
                .quantity(inputItemQuantity)
                .stock(baseStock)
                .build();

        // when
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                   try {
                       orderService.orderProcess(item, inputItemNo, inputItemQuantity, orderMap);
                   } finally {
                       latch.countDown();
                   }
                }
            );
        }
        latch.await();

        // then
        assertThat(orderMap.get(inputItemNo).getItem().getQuantity()).isEqualTo(sumQuantity);
        assertThat(orderMap.get(inputItemNo).getItem().getStock()).isEqualTo(baseStock - sumQuantity);
    }

}
