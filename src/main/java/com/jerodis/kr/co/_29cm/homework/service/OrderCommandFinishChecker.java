package com.jerodis.kr.co._29cm.homework.service;

import org.springframework.util.StringUtils;

public class OrderCommandFinishChecker {
    public static boolean isFinish(String s) {
        return s.length() == 1 && StringUtils.containsWhitespace(s);
    }
}
