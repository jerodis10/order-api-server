package com.jerodis.kr.co._29cm.homework;

import java.io.IOException;
import java.util.List;

public class Application2 {

	public static void main(String[] args) throws IOException {
		OrderRepository orderRepository = new FileOrderRepository();
		OrderService orderService = new OrderService(orderRepository);
		InputReader inputReader = new InputReader();

		// 콘솔 입력 (o or q)
		while(true) {
			System.out.printf("입력(%s[%s]: %s, %s[%s]: %s) : ",
					OrderCommand.ORDER.commandChar, OrderCommand.ORDER.commandString, OrderCommand.ORDER.commandWord,
					OrderCommand.QUIT.commandChar, OrderCommand.QUIT.commandString, OrderCommand.QUIT.commandWord);

			OrderCommand orderCommand = OrderCommand.of(inputReader.read());
			if (orderCommand.equals(OrderCommand.ORDER)) {
				// 전체 상품 출력
				PrinterUtils.print(orderService.getItems());
				// 주문 요청 출력
				PrinterUtils.print(orderService.requestOrder());
			} else if (orderCommand.equals(OrderCommand.QUIT)) {
				return;
			}
		}
	}

}
