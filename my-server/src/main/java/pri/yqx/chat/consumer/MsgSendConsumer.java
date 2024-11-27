package pri.yqx.chat.consumer;

import javax.annotation.Resource;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pri.yqx.chat.dao.ChatContentDao;
import pri.yqx.chat.domain.dto.ChatMsgDto;
import pri.yqx.chat.domain.entity.ChatContent;
import pri.yqx.chat.domain.vo.ChatMsgVo;
import pri.yqx.common.util.MyBeanUtils;
import pri.yqx.websocket.service.WebSocketService;
import pri.yqx.websocket.service.WsMsgType;

@Component
@RocketMQMessageListener(
    topic = "chat_msg",
    consumerGroup = "chat_group"
)
public class MsgSendConsumer implements RocketMQListener<ChatMsgDto> {
    private static final Logger log = LoggerFactory.getLogger(MsgSendConsumer.class);
    @Resource
    private WebSocketService webSocketService;
    @Resource
    private ChatContentDao chatContentDao;


    public MsgSendConsumer() {
    }

    public void onMessage(ChatMsgDto chatMsgDto) {
        log.warn("我收到消息了={}", chatMsgDto);
        ChatContent chatContent = (ChatContent)this.chatContentDao.getById(chatMsgDto.getChatId());
        ChatMsgVo chatMsgVo = (ChatMsgVo)MyBeanUtils.copyProperties(chatContent, new ChatMsgVo());
        this.webSocketService.sendMsg(chatMsgVo.getReceiveUserId(), chatMsgVo, WsMsgType.CHAT_MSG);
    }
}