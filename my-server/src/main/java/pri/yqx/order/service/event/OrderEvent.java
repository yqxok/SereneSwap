package pri.yqx.order.service.event;

import org.springframework.context.ApplicationEvent;
import pri.yqx.order.domain.dto.OrderMsgDto;

public class OrderEvent extends ApplicationEvent {
    private OrderMsgDto orderMsgDto;

    public OrderEvent(Object source, OrderMsgDto orderMsgDto) {
        super(source);
        this.orderMsgDto = orderMsgDto;
    }

    public OrderMsgDto getOrderMsgDto() {
        return this.orderMsgDto;
    }
}