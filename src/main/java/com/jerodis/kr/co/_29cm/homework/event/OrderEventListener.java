package com.jerodis.kr.co._29cm.homework.event;

public class OrderEventListener {

    public void handle(Event event) {
        event.service();
    }
}
