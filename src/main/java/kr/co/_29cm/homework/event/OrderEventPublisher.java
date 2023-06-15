package kr.co._29cm.homework.event;

import kr.co._29cm.homework.common.OrderCommand;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderEventPublisher {

    private final OrderEventListener orderEventListener;

    public boolean publishEvent(OrderCommand orderCommand) {
        if (orderCommand.equals(OrderCommand.ORDER)) {
            orderEventListener.handle(new OrderEvent());
            return true;
        } else if(orderCommand.equals(OrderCommand.QUIT)) {
            orderEventListener.handle(new QuitEvent());
            return false;
        }

        return false;
    }
}
