package pri.yqx.order.service.event.listner;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import pri.yqx.order.domain.dto.OrderMsgDto;
import pri.yqx.order.service.event.OrderEvent;
import pri.yqx.rocketmq.produce.MqProducer;

@Component
public class OrderEventListner {
    @Autowired
    private MqProducer mqProducer;

    public OrderEventListner() {
    }

    @Async
    @TransactionalEventListener({OrderEvent.class})
    public void sendOrderMsg(OrderEvent orderEvent) {
        OrderMsgDto orderMsgDto = orderEvent.getOrderMsgDto();
        this.mqProducer.sendMsg("order_msg", orderMsgDto);
    }
}