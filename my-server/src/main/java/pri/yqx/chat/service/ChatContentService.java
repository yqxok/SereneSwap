package pri.yqx.chat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pri.yqx.chat.domain.dto.req.ChatContentReq;
import pri.yqx.chat.domain.dto.req.ChatCursorReq;
import pri.yqx.chat.domain.entity.ChatContent;
import pri.yqx.chat.domain.vo.ChatContentVo;
import pri.yqx.common.domain.response.CursorPageVo;

public interface ChatContentService extends IService<ChatContent> {
    void updateContentRead(Long userId, Long goodId, Long otherId);

    CursorPageVo<ChatContentVo> getCursorPage(Long userId, ChatCursorReq gDto);

    Long sendMsg(Long userId, ChatContentReq chatContentDto);

    ChatContentVo getChatContent(Long id);
}