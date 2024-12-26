package pri.yqx.msg.consumer;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pri.yqx.msg.dao.OrderMsgDao;
import pri.yqx.msg.domain.entity.OrderMsg;
import pri.yqx.msg.domain.enums.MsgRoomType;
import pri.yqx.msg.domain.vo.OrderMsgVo;
import pri.yqx.msg.service.MsgRoomService;
import pri.yqx.msg.service.OrderMsgService;
import pri.yqx.order.domain.dto.OrderMsgDto;
import pri.yqx.order.domain.entity.OrderUnion;
import pri.yqx.order.service.OrderService;
import pri.yqx.websocket.service.WebSocketService;
import pri.yqx.websocket.service.WsMsgType;

@Component
@RocketMQMessageListener(
    topic = "order_msg",
    consumerGroup = "order_group"
)
public class OrderConsumer implements RocketMQListener<OrderMsgDto> {
    private static final Logger log = LoggerFactory.getLogger(OrderConsumer.class);
    @Autowired
    private OrderMsgDao orderMsgDao;
    @Autowired
    private OrderMsgService orderMsgService;
    @Autowired
    private WebSocketService webSocketService;
    @Autowired
    private MsgRoomService msgRoomService;
    @Autowired
    private OrderService orderService;

    public OrderConsumer() {
    }

    @Transactional
    public void onMessage(OrderMsgDto orderMsgDto) {
        if (this.orderMsgDao.isExit(orderMsgDto.getOrderId(), orderMsgDto.getStatus())) {
            log.warn("消息被重复消费了,{}", orderMsgDto);
        } else {
            log.info("我收到订单消息了:{}", orderMsgDto);
            OrderUnion orderUnion = this.orderService.getOrder(orderMsgDto.getReceiverId(), orderMsgDto.getOrderId());
            long id = IdWorker.getId();
            this.orderMsgDao.save(this.build(orderMsgDto, orderUnion, id));
            this.msgRoomService.noReadMsgAdd(orderMsgDto.getReceiverId(), 1,MsgRoomType.TOTAL_MSG_ROOM, MsgRoomType.ORDER_ROOM);
            OrderMsgVo orderMsgVo = this.orderMsgService.getOrderMsgVo(id);
            this.webSocketService.sendMsg(orderMsgDto.getReceiverId(), orderMsgVo, WsMsgType.ORDER_MSG);
        }
    }

    private OrderMsg build(OrderMsgDto orderMsgDto, OrderUnion orderUnion, long id) {
        return new OrderMsg().setOrderMsgId(id).setOrderId(orderMsgDto.getOrderId())
                .setStatus(orderMsgDto.getStatus()).setSenderId(orderUnion.getDealUserId())
                .setReceiverId(orderUnion.getUserId()).setGoodId(orderUnion.getGoodId());
    }
}