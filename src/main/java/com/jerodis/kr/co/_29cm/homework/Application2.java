package com.jerodis.kr.co._29cm.homework;

import com.jerodis.kr.co._29cm.homework.common.InputReader;
import com.jerodis.kr.co._29cm.homework.common.OrderCommand;
import com.jerodis.kr.co._29cm.homework.common.Printer;
import com.jerodis.kr.co._29cm.homework.common.SystemPrinter;
import com.jerodis.kr.co._29cm.homework.exception.InvalidCommandException;
import com.jerodis.kr.co._29cm.homework.exception.ItemNotFoundException;
import com.jerodis.kr.co._29cm.homework.exception.SoldOutException;
import com.jerodis.kr.co._29cm.homework.repository.FileOrderRepository;
import com.jerodis.kr.co._29cm.homework.repository.OrderRepository;
import com.jerodis.kr.co._29cm.homework.service.OrderService;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

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

				//			log.info("입력({}[{}]: {}, {}[{}]: {}) : ",
				//					OrderCommand.ORDER.commandChar, OrderCommand.ORDER.commandString, OrderCommand.ORDER.commandWord,
				//					OrderCommand.QUIT.commandChar, OrderCommand.QUIT.commandString, OrderCommand.QUIT.commandWord);

				//			System.out.printf("입력(%s[%s]: %s, %s[%s]: %s) : ",
				//					OrderCommand.ORDER.commandChar, OrderCommand.ORDER.commandString, OrderCommand.ORDER.commandWord,
				//					OrderCommand.QUIT.commandChar, OrderCommand.QUIT.commandString, OrderCommand.QUIT.commandWord);

				OrderCommand orderCommand = OrderCommand.of(inputReader.read());
				if (orderCommand.equals(OrderCommand.ORDER)) {
					// 전체 상품 출력
					//				PrinterUtils.print(orderService.getItems());
					printer.print(orderService.getItems());
					// 주문 요청 출력
					//				PrinterUtils.print(orderService.requestOrder());
					printer.print(orderService.requestOrder());
				} else if (orderCommand.equals(OrderCommand.QUIT)) {
					printer.println("고객님의 주문에 감사드립니다.");
					return;
				}
			} catch (ItemNotFoundException | InvalidCommandException | SoldOutException e) {
				printer.println(e.getMessage());
			} catch (IOException e) {
				printer.println("IOException 발생 : " + e.getMessage());
			} catch (Exception e) {
				log.info("예외 발생: {}", e.getMessage());
			}
		}
	}

}
