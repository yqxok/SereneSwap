package pri.yqx.rocketmq.produce;

import javax.annotation.Resource;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class MqProducer {
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    public MqProducer() {
    }

    public void sendMsg(String topic, Object data) {
        Message<Object> message = MessageBuilder.withPayload(data).build();
        this.rocketMQTemplate.send(topic, message);
    }
}