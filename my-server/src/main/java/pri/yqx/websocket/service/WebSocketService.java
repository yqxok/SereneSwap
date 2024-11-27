package pri.yqx.websocket.service;

import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class WebSocketService {
    private static final Logger log = LoggerFactory.getLogger(WebSocketService.class);
    private final ConcurrentHashMap<Long, Channel> ONLINE_USER = new ConcurrentHashMap();
    private final ConcurrentHashMap<Channel, Long> CHANNELS = new ConcurrentHashMap();

    public WebSocketService() {
    }

    public void addUserOnline(Long userId, Channel channel) {
        this.ONLINE_USER.put(userId, channel);
        this.CHANNELS.put(channel, userId);
        log.info("当前用户有{}", this.ONLINE_USER);
    }

    public void userOffline(Channel channel) {
        Long userId = (Long)this.CHANNELS.get(channel);
        if (!Objects.isNull(userId)) {
            log.info("{}用户已下线", userId);
            this.CHANNELS.remove(channel);
            this.ONLINE_USER.remove(userId);
        }

    }

    public void sendMsg(Long userId, Object data, WsMsgType wsMsgType) {
        Channel channel = (Channel)this.ONLINE_USER.get(userId);
        if (Objects.isNull(channel)) {
            log.warn("websocket发送消息失败,无法找到对应的channel");
        } else {
            WsMsgDto wsMsgDto = new WsMsgDto(wsMsgType.getValue(), data);
            channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(wsMsgDto)));
        }
    }
}