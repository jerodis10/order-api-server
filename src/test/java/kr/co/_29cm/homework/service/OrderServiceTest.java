package kr.co._29cm.homework.service;

import kr.co._29cm.homework.BaseTest;
import kr.co._29cm.homework.common.FileReader;
import kr.co._29cm.homework.common.InputReader;
import kr.co._29cm.homework.common.Printer;
import kr.co._29cm.homework.common.SystemPrinter;
import kr.co._29cm.homework.domain.Order;
import kr.co._29cm.homework.domain.OrderDetail;
import kr.co._29cm.homework.domain.Stock;
import kr.co._29cm.homework.exception.InvalidCommandException;
import kr.co._29cm.homework.exception.SoldOutException;
import kr.co._29cm.homework.repository.FileOrderRepository;
import kr.co._29cm.homework.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderServiceTest extends BaseTest {

    OrderRepository orderRepository = FileOrderRepository.init();
    private final Printer printer = new SystemPrinter();
    private final InputReader inputReader = new InputReader();
    private final OrderService orderService = new OrderService(orderRepository, printer, inputReader);


    @DisplayName("단일 상품, 단일 주문 요청 / 재고수량 이상 주문시 예외 발생")
    @Test
    void throwException_SingleItem_withSingleOrder_moreStock() {
        // given
        Map<String, OrderDetail> orderMap = new HashMap<>();
        Long orderQuantity = 20L;
        Long baseStock = 10L;

        Stock stock = sut.giveMeBuilder(Stock.class)
                .set("stockQuantity", baseStock)
                .sample();

        // when then
        assertThatThrownBy(() -> ReflectionTestUtils.invokeMethod(orderService, "orderProcess",
                stock, orderQuantity, orderMap)).isInstanceOf(SoldOutException.class);
    }

    @DisplayName("단일 상품, 복수 주문 요청 / 재고수량 이상 주문시 예외 발생")
    @Test
    void throwException_SingleItem_withMultiOrder_moreStock() {
        // given
        Map<String, OrderDetail> orderMap = new HashMap<>();
        Long orderQuantity = 6L;
        Long baseStock = 10L;

        Stock stock = sut.giveMeBuilder(Stock.class)
                .set("stockQuantity", baseStock)
                .sample();

        // when then
        ReflectionTestUtils.invokeMethod(orderService, "orderProcess", stock, orderQuantity, orderMap);
        assertThatThrownBy(() -> ReflectionTestUtils.invokeMethod(orderService, "orderProcess", stock, orderQuantity, orderMap))
                .isInstanceOf(SoldOutException.class);
    }

    @DisplayName("존재하지 않는 상품 조회 시 예외 발생")
    @Test
    void throwException_whenNotFoundItem() {
        // when then
        assertThatThrownBy(() -> orderRepository.findStockByItemNo("123")).isInstanceOf(InvalidCommandException.class);
    }

    @DisplayName("멀티쓰레드 요청, 상품 재고 부족시 예외 발생")
    @Test
    void throwException_whenNotEnoughStock_withMultiThread() throws InterruptedException {
        // given
        int threadCount = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        AtomicInteger exceptionCounter = new AtomicInteger(0);
        CountDownLatch latch = new CountDownLatch(threadCount);
        Map<String, OrderDetail> orderMap = new HashMap<>();

        Long orderQuantity = 1L;
        Long baseStock = 500L;
        Stock stock = sut.giveMeBuilder(Stock.class)
                .set("stockQuantity", baseStock)
                .sample();

        // when
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                    try {
                        ReflectionTestUtils.invokeMethod(orderService, "orderProcess", stock, orderQuantity, orderMap);
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
        Map<Long, OrderDetail> orderMap = new HashMap<>();
        Long price = 1L;
        Long orderQuantity = 2L;
        Long baseStock = 7L;
        Long deliveryFee = price * orderQuantity > MINIMUM_SHIPPING_AMOUNT ? BASIC_SHIPPING_AMOUNT : SHIPPING_AMOUNT;

        Stock stock = sut.giveMeBuilder(Stock.class)
                .set("price", price)
                .set("stockQuantity", baseStock)
                .sample();

        // when
        ReflectionTestUtils.invokeMethod(orderService, "orderProcess", stock, orderQuantity, orderMap);
        Order order = new Order(new ArrayList<>(orderMap.values()));

        // then
        assertThat(orderMap.get(stock.getItemNo()).getOrderQuantity()).isEqualTo(orderQuantity);
        assertThat(stock.getStockQuantity()).isEqualTo(baseStock - orderQuantity);
        assertThat(order.getDeliveryFee()).isEqualTo(deliveryFee);
        assertThat(order.getOrderAmount()).isEqualTo(price * orderQuantity);
        assertThat(order.getPayment()).isEqualTo(price * orderQuantity + order.getDeliveryFee());
    }

    @DisplayName("단일 상품, 복수 주문 요청")
    @Test
    void success_SingleItem_withMultiOrder() {
        // given
        Map<Long, OrderDetail> orderMap = new HashMap<>();
        int count = 3;
        Long price = 1L;
        Long orderQuantity = 2L;
        Long baseStock = 10L;
        Long sumCost = price * orderQuantity * count;
        Long deliveryFee = sumCost > MINIMUM_SHIPPING_AMOUNT ? BASIC_SHIPPING_AMOUNT : SHIPPING_AMOUNT;

        Stock stock = sut.giveMeBuilder(Stock.class)
                .set("price", price)
                .set("stockQuantity", baseStock)
                .sample();

        // when
        for (int i = 0; i < count; i++) {
            ReflectionTestUtils.invokeMethod(orderService, "orderProcess", stock, orderQuantity, orderMap);
        }
        Order order = new Order(new ArrayList<>(orderMap.values()));

        // then
        assertThat(orderMap.get(stock.getItemNo()).getOrderQuantity()).isEqualTo(orderQuantity * count);
        assertThat(stock.getStockQuantity()).isEqualTo(baseStock - orderQuantity * count);
        assertThat(order.getDeliveryFee()).isEqualTo(deliveryFee);
        assertThat(order.getOrderAmount()).isEqualTo(sumCost);
        assertThat(order.getPayment()).isEqualTo(sumCost + order.getDeliveryFee());
    }

    @DisplayName("복수 상품, 단일 주문 요청")
    @Test
    void success_MultiItem_withSingleOrder() {
        // given
        Map<Long, OrderDetail> orderMap = new HashMap<>();

        Long price = 1L;
        Long orderQuantity = 2L;
        Long baseStock = 10L;
        Stock stock = sut.giveMeBuilder(Stock.class)
                .set("price", price)
                .set("stockQuantity", baseStock)
                .sample();

        Long price2 = 2L;
        Long orderQuantity2 = 3L;
        Long baseStock2 = 20L;
        Stock stock2 = sut.giveMeBuilder(Stock.class)
                .set("price", price2)
                .set("stockQuantity", baseStock2)
                .sample();

        Long sumCost = (price * orderQuantity) + (price2 * orderQuantity2);
        Long deliveryFee = sumCost > MINIMUM_SHIPPING_AMOUNT ? BASIC_SHIPPING_AMOUNT : SHIPPING_AMOUNT;

        // when
        ReflectionTestUtils.invokeMethod(orderService, "orderProcess", stock, orderQuantity, orderMap);
        ReflectionTestUtils.invokeMethod(orderService, "orderProcess", stock2, orderQuantity2, orderMap);

        Order order = new Order(new ArrayList<>(orderMap.values()));

        // then
        assertThat(orderMap.get(stock.getItemNo()).getOrderQuantity()).isEqualTo(orderQuantity);
        assertThat(stock.getStockQuantity()).isEqualTo(baseStock - orderQuantity);

        assertThat(orderMap.get(stock2.getItemNo()).getOrderQuantity()).isEqualTo(orderQuantity2);
        assertThat(stock2.getStockQuantity()).isEqualTo(baseStock2 - orderQuantity2);

        assertThat(order.getDeliveryFee()).isEqualTo(deliveryFee);
        assertThat(order.getOrderAmount()).isEqualTo(sumCost);
        assertThat(order.getPayment()).isEqualTo(sumCost + order.getDeliveryFee());
    }

    @DisplayName("복수 상품, 복수 주문 요청")
    @Test
    void success_MultiItem_withMultiOrder() {
        // given
        Map<Long, OrderDetail> orderMap = new HashMap<>();
        int count = 3;

        Long price = 1L;
        Long orderQuantity = 2L;
        Long baseStock = 10L;
        Stock stock = sut.giveMeBuilder(Stock.class)
                .set("price", price)
                .set("stockQuantity", baseStock)
                .sample();

        Long price2 = 2L;
        Long orderQuantity2 = 3L;
        Long baseStock2 = 20L;
        Stock stock2 = sut.giveMeBuilder(Stock.class)
                .set("price", price2)
                .set("stockQuantity", baseStock2)
                .sample();

        Long sumCost = ((price * orderQuantity) + (price2 * orderQuantity2)) * count;
        Long deliveryFee = sumCost > MINIMUM_SHIPPING_AMOUNT ? BASIC_SHIPPING_AMOUNT : SHIPPING_AMOUNT;

        // when
        for (int i = 0; i < count; i++) {
            ReflectionTestUtils.invokeMethod(orderService, "orderProcess", stock, orderQuantity, orderMap);
            ReflectionTestUtils.invokeMethod(orderService, "orderProcess", stock2, orderQuantity2, orderMap);
        }
        Order order = new Order(new ArrayList<>(orderMap.values()));

        // then
        assertThat(orderMap.get(stock.getItemNo()).getOrderQuantity()).isEqualTo(orderQuantity * count);
        assertThat(stock.getStockQuantity()).isEqualTo(baseStock - orderQuantity * count);

        assertThat(orderMap.get(stock2.getItemNo()).getOrderQuantity()).isEqualTo(orderQuantity2 * count);
        assertThat(stock2.getStockQuantity()).isEqualTo(baseStock2 - orderQuantity2 * count);

        assertThat(order.getDeliveryFee()).isEqualTo(deliveryFee);
        assertThat(order.getOrderAmount()).isEqualTo(sumCost);
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
        List<Stock> stocks = orderService.getItems();

        // then
        assertThat(stocks.size()).isEqualTo(fileList.size());
    }

    @ParameterizedTest
    @ValueSource(strings = {"648418", "782858", "502480", "778422"})
    @DisplayName("단일 상품 조회")
    void success_whenFindOneItem(String itemNo) throws IOException {
        // given when
        Stock stock = orderRepository.findStockByItemNo(itemNo);

        // then
        assertThat(stock).isNotNull();
    }

    @DisplayName("멀티쓰레드 요청 시 상품 재고 이동 테스트")
    @Test
    void success_whenNotEnoughStock_withMultiThread() throws InterruptedException {
        // given
        int threadCount = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);
        Map<Long, OrderDetail> orderMap = new HashMap<>();

        Long orderQuantity = 1L;
        Long baseStock = 1000L;
        Long sumQuantity = orderQuantity * threadCount;
        Stock stock = sut.giveMeBuilder(Stock.class)
                .set("stockQuantity", baseStock)
                .sample();

        // when
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                   try {
                       ReflectionTestUtils.invokeMethod(orderService, "orderProcess", stock, orderQuantity, orderMap);
                   } finally {
                       latch.countDown();
                   }
                }
            );
        }
        latch.await();

        // then
        assertThat(orderMap.get(stock.getItemNo()).getOrderQuantity()).isEqualTo(sumQuantity);
        assertThat(stock.getStockQuantity()).isEqualTo(baseStock - sumQuantity);
    }

}
