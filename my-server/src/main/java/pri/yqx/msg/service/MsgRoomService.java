package pri.yqx.msg.service;

import pri.yqx.msg.domain.dto.req.MsgTypeReq;
import pri.yqx.msg.domain.enums.MsgRoomType;

public interface MsgRoomService {
    void createMsgRoom(Long userId);

    void msgRead(Long userId, MsgTypeReq msgTypeReq);

    Integer getNoReadMsgNum(Long userId, MsgRoomType roomType);

    void totalMsgRead(Long userId, Integer num);

    void noReadMsgAdd(Long userId, Integer num, MsgRoomType... types);
}