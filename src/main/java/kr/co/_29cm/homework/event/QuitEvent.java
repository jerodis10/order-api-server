package kr.co._29cm.homework.event;

import kr.co._29cm.homework.common.Printer;
import kr.co._29cm.homework.common.SystemPrinter;

public class QuitEvent implements Event {

    Printer printer = new SystemPrinter();

    @Override
    public void service() {
        printer.println("고객님의 주문에 감사드립니다.");
    }
}
