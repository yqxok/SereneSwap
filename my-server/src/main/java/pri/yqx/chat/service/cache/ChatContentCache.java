
package pri.yqx.chat.service.cache;

import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import pri.yqx.chat.dao.ChatContentDao;
import pri.yqx.chat.domain.dto.req.ChatCursorReq;
import pri.yqx.chat.domain.entity.ChatContent;
import pri.yqx.common.domain.response.CursorPageVo;

@Component
public class ChatContentCache {
    private static final Logger log = LoggerFactory.getLogger(ChatContentCache.class);
    @Autowired
    private ChatContentDao chatContentDao;

    public ChatContentCache() {
    }

    @Cacheable(value = {"chatContent"}, key = "'myChatContent'")
    public CursorPageVo<ChatContent> getCursorPage(Long userId, ChatCursorReq gDto, LocalDateTime earlistTime) {
        log.info("不走数据库");
        return this.chatContentDao.getCursorPage(userId, gDto, earlistTime);
    }
}
