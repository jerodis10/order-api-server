package com.jerodis.kr.co._29cm.homework.common;

import com.jerodis.kr.co._29cm.homework.exception.CommonException;
import com.jerodis.kr.co._29cm.homework.exception.CommonExceptionStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputReader {
    private final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public String read() {
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            throw new CommonException(CommonExceptionStatus.IO_EXCEPTION);
        }
    }
}
