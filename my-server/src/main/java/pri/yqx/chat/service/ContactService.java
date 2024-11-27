package pri.yqx.chat.service;

import pri.yqx.chat.domain.dto.req.ChatContentReq;
import pri.yqx.chat.domain.vo.ContactVo;
import pri.yqx.common.domain.response.CursorPageVo;

public interface ContactService {
    void createContact(ChatContentReq cReq);

    CursorPageVo<ContactVo> getCursorContact(Long userId, Long cursor, Integer pageSize);

    void deleteContact(Long userId, Long roomId);
}