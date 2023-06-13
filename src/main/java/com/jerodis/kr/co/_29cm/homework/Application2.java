package com.jerodis.kr.co._29cm.homework;

import com.jerodis.kr.co._29cm.homework.common.InputReader;
import com.jerodis.kr.co._29cm.homework.service.OrderCommand;
import com.jerodis.kr.co._29cm.homework.common.Printer;
import com.jerodis.kr.co._29cm.homework.common.SystemPrinter;
import com.jerodis.kr.co._29cm.homework.exception.CommonException;
import com.jerodis.kr.co._29cm.homework.exception.InvalidCommandException;
import com.jerodis.kr.co._29cm.homework.exception.SoldOutException;
import com.jerodis.kr.co._29cm.homework.repository.FileOrderRepository;
import com.jerodis.kr.co._29cm.homework.repository.OrderRepository;
import com.jerodis.kr.co._29cm.homework.service.OrderService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Application2 {

	public static void main(String[] args) {
		OrderRepository orderRepository = new FileOrderRepository();
		Printer printer = new SystemPrinter();
//		Printer printer = new LogPrinter();
		InputReader inputReader = new InputReader();
		OrderService orderService = new OrderService(orderRepository, printer, inputReader);

		while(true) {
			try {
				printer.initInput();
				OrderCommand orderCommand = OrderCommand.of(inputReader.read());
				if (orderCommand.equals(OrderCommand.ORDER)) {
					// 전체 상품 출력
					printer.print(orderService.getItems());

					// 주문 요청 출력
					printer.print(orderService.requestOrder());

				} else if (orderCommand.equals(OrderCommand.QUIT)) {
					printer.println("고객님의 주문에 감사드립니다.");
					return;
				}
			} catch (SoldOutException e) {
				printer.println(e.getMessage());
			} catch (InvalidCommandException e) {
				printer.println("InvalidCommandException 발생 : " + e.getMessage() + " -> " + e.getErrorMessage());
			} catch (CommonException e) {
				printer.println("CommonException 발생 : " + e.getMessage());
			} catch (Exception e) {
				log.info("예외 발생: {}", e.getMessage());
			}
		}
	}

}
