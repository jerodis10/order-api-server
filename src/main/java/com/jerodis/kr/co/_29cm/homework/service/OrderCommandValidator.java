package com.jerodis.kr.co._29cm.homework.service;

import com.jerodis.kr.co._29cm.homework.exception.InvalidCommandException;
import com.jerodis.kr.co._29cm.homework.exception.InvalidCommandExceptionStatus;
import org.springframework.util.StringUtils;

import static com.jerodis.kr.co._29cm.homework.common.NumberUtil.isNumeric;

public class OrderCommandValidator {
    public static boolean validateItemNo(String inputItemNo) {
        if (!StringUtils.hasText(inputItemNo)) return false;
        if (!isNumeric(inputItemNo)) {
            throw new InvalidCommandException(InvalidCommandExceptionStatus.INVALID_ITEM_NO, inputItemNo);
        }

        return true;
    }

    public static boolean validateQuantity(String inputQuantity) {
        if (!StringUtils.hasText(inputQuantity)) return false;
        if (!isNumeric(inputQuantity)) {
            throw new InvalidCommandException(InvalidCommandExceptionStatus.INVALID_QUANTITY, inputQuantity);
        }
        if (Long.parseLong(inputQuantity) <= 0L) {
            throw new InvalidCommandException(InvalidCommandExceptionStatus.NEGATIVE_QUANTITY, inputQuantity);
        }

        return true;
    }

}
