package pri.yqx.msg.service;

import pri.yqx.msg.domain.dto.req.MsgTypeReq;
import pri.yqx.msg.domain.enums.MsgRoomType;

public interface MsgRoomService {
    /**
     * 创建消息房间
     * @param userId
     */
    void createMsgRoom(Long userId);

    /**
     * 消息改为已读
     * @param userId
     * @param msgTypeReq
     */
    void msgRead(Long userId, MsgTypeReq msgTypeReq);

    /**
     * 获取未读消息数
     * @param userId
     * @param roomType
     * @return
     */
    Integer getNoReadMsgNum(Long userId, MsgRoomType roomType);

    /**
     * 总消息已读数-num
     * @param userId
     * @param num
     */
    void totalMsgRead(Long userId, Integer num);

    /**
     * 未读消息数量+num
     * @param userId
     * @param num
     * @param types
     */
    void noReadMsgAdd(Long userId, Integer num, MsgRoomType... types);
}