package com.jerodis.kr.co._29cm.homework;

import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Concurrent;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;
import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RepeatingRuleTest {
    @Rule
    public RepeatingRule rule = new RepeatingRule();

    private static int counter = 0;

    @Test
    @Concurrent(count = 100)
    @Repeating(repetition = 100)
    public void annotatedTest() {
        counter++;
    }

    @AfterClass
    public static void annotatedTestRunsMultipleTimes() {
//        assertThat(counter).isEqualTo(9);
        assertThat(counter).isEqualTo(100);
    }
}
