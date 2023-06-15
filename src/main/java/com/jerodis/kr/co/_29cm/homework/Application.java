package com.jerodis.kr.co._29cm.homework;

import com.jerodis.kr.co._29cm.homework.common.InputReader;
import com.jerodis.kr.co._29cm.homework.common.OrderCommand;
import com.jerodis.kr.co._29cm.homework.common.Printer;
import com.jerodis.kr.co._29cm.homework.common.SystemPrinter;
import com.jerodis.kr.co._29cm.homework.event.OrderEventListener;
import com.jerodis.kr.co._29cm.homework.event.OrderEventPublisher;
import com.jerodis.kr.co._29cm.homework.exception.CommonException;
import com.jerodis.kr.co._29cm.homework.exception.InvalidCommandException;
import com.jerodis.kr.co._29cm.homework.exception.NoRequestOrderException;
import com.jerodis.kr.co._29cm.homework.exception.SoldOutException;
import com.jerodis.kr.co._29cm.homework.repository.FileOrderRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Application {

	public static void main(String[] args) {
		Printer printer = new SystemPrinter();
		InputReader inputReader = new InputReader();
		OrderEventListener orderEventListener = new OrderEventListener();
		OrderEventPublisher orderEventPublisher = new OrderEventPublisher(orderEventListener);
		FileOrderRepository.init();

		while(true) {
			try {
				printer.initInput();
				OrderCommand orderCommand = OrderCommand.of(inputReader.read());
				if(!orderEventPublisher.publishEvent(orderCommand)) break;

			} catch (SoldOutException | NoRequestOrderException e) {
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
