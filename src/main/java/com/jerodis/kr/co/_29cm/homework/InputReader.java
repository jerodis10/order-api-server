package com.jerodis.kr.co._29cm.homework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputReader {
    private final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public String read() throws IOException {
        return bufferedReader.readLine();
    }
}
