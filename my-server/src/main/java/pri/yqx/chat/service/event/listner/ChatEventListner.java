package pri.yqx.chat.service.event.listner;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import pri.yqx.chat.domain.dto.ChatMsgDto;
import pri.yqx.chat.service.event.ChatEvent;
import pri.yqx.rocketmq.produce.MqProducer;

@Component
public class ChatEventListner {
    @Autowired
    private MqProducer mqProducer;

    public ChatEventListner() {
    }

    @Async
    @TransactionalEventListener({ChatEvent.class})
    public void sendMsg(ChatEvent chatEvent) {
        this.mqProducer.sendMsg("chat_msg", new ChatMsgDto(chatEvent.getChatId()));
    }
}