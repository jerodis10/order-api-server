package com.jerodis.kr.co._29cm.homework;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Application2 {

	public static void main(String[] args) throws IOException {
		OrderService orderService = new OrderService();
		StringBuffer stringBuffer = new StringBuffer();

		// 콘솔 입력 (o or q)
		while(true) {
			System.out.print("입력(o[order]: 주문, q[quit]: 종료) : ");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

			// 콘솔 입력 validation 체크
			// 콘솔 주문 상세 입력 (o or q)
			if (!orderService.findAllOrder(br.readLine())) {
				return;
			}

			// 콘솔 입력 validation 체크 (null, 중복, 유효성(재고 수량 내, 1 이상))
			// 주문 상세 data 콘솔 출력
			System.out.println("");

			orderService.findOrder();
		}
	}

}
