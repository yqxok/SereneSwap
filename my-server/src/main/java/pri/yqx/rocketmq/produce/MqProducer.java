package pri.yqx.rocketmq.produce;


import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class MqProducer {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;



    public void sendMsg(String topic, Object data) {
        Message<Object> message = MessageBuilder.withPayload(data).build();

        this.rocketMQTemplate.syncSend(topic, message);
    }
}