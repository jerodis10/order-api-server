package com.jerodis.kr.co._29cm.homework.event;

import com.jerodis.kr.co._29cm.homework.common.InputReader;
import com.jerodis.kr.co._29cm.homework.common.Printer;
import com.jerodis.kr.co._29cm.homework.common.SystemPrinter;
import com.jerodis.kr.co._29cm.homework.repository.FileOrderRepository;
import com.jerodis.kr.co._29cm.homework.repository.OrderRepository;
import com.jerodis.kr.co._29cm.homework.service.OrderService;

public class OrderEvent implements Event {

    OrderRepository orderRepository = new FileOrderRepository(FileOrderRepository.stockMap);
    Printer printer = new SystemPrinter();
    InputReader inputReader = new InputReader();
    OrderService orderService = new OrderService(orderRepository, printer, inputReader);

    @Override
    public void service() {
        // 전체 상품 출력
        printer.print(orderService.getItems());

        // 주문 요청 출력
        printer.print(orderService.requestOrder());
    }

}
