package com.jerodis.kr.co._29cm.homework.service;

import com.jerodis.kr.co._29cm.homework.exception.InvalidCommandException;
import com.jerodis.kr.co._29cm.homework.exception.InvalidCommandExceptionStatus;
import com.jerodis.kr.co._29cm.homework.exception.NoRequestOrderException;
import org.springframework.util.StringUtils;

import static com.jerodis.kr.co._29cm.homework.common.NumberUtil.isNumeric;

public class OrderCommandValidator {
    public static boolean validateItemNo(String inputItemNo) {
        if(OrderCommandFinishChecker.isFinish(inputItemNo)) return false;
        if (!StringUtils.hasText(inputItemNo)) throw new NoRequestOrderException("주문한 상품이 존재하지 않습니다.");
        if (!isNumeric(inputItemNo)) {
            throw new InvalidCommandException(InvalidCommandExceptionStatus.INVALID_ITEM_NO, inputItemNo);
        }

        return true;
    }

    public static boolean validateQuantity(String inputQuantity) {
        if(OrderCommandFinishChecker.isFinish(inputQuantity)) return false;
        if (!StringUtils.hasText(inputQuantity)) throw new NoRequestOrderException("주문한 상품이 존재하지 않습니다.");
        if (!isNumeric(inputQuantity)) {
            throw new InvalidCommandException(InvalidCommandExceptionStatus.INVALID_QUANTITY, inputQuantity);
        }
        if (Long.parseLong(inputQuantity) <= 0L) {
            throw new InvalidCommandException(InvalidCommandExceptionStatus.NEGATIVE_QUANTITY, inputQuantity);
        }

        return true;
    }

}
