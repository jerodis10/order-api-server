package com.jerodis.kr.co._29cm.homework.domain;

import com.jerodis.kr.co._29cm.homework.exception.SoldOutException;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static com.jerodis.kr.co._29cm.homework.common.NumberUtil.isNumeric;

@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Item {

	@NonNull
	private final Long itemNo;

	@NonNull
	private final String itemName;

	@NonNull
	private final Long price;


	@Builder
	public Item(Long itemNo, String itemName, Long price) {
		this.itemNo = itemNo;
		this.itemName = itemName;
		this.price = price;
	}

}
