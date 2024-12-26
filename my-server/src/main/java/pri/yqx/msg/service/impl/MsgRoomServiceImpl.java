//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.msg.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pri.yqx.msg.dao.MsgRoomDao;
import pri.yqx.msg.domain.dto.req.MsgTypeReq;
import pri.yqx.msg.domain.entity.MsgRoom;
import pri.yqx.msg.domain.enums.MsgRoomType;
import pri.yqx.msg.service.MsgRoomService;

@Service
@Transactional
public class MsgRoomServiceImpl implements MsgRoomService {
    @Autowired
    private MsgRoomDao msgRoomDao;



    public void createMsgRoom(Long userId) {
        MsgRoom msgRoom = (new MsgRoom()).setUserId(userId).setType(MsgRoomType.COMMENT_ROOM.getType()).setNoReadNum(0);
        MsgRoom msgRoom1 = (new MsgRoom()).setUserId(userId).setType(MsgRoomType.ORDER_ROOM.getType()).setNoReadNum(0);
        MsgRoom msgRoom2 = (new MsgRoom()).setUserId(userId).setType(MsgRoomType.TOTAL_MSG_ROOM.getType()).setNoReadNum(0);
        this.msgRoomDao.saveBatch(Arrays.asList(msgRoom, msgRoom1, msgRoom2));
    }

    public void msgRead(Long userId, MsgTypeReq msgTypeReq) {
        MsgRoomType type = MsgRoomType.getMsgRoomType(msgTypeReq.getType());
        List<MsgRoom> msgRooms = this.msgRoomDao.getMsgRooms(userId, type, MsgRoomType.TOTAL_MSG_ROOM);
        MsgRoom room = null;
        MsgRoom totalRoom = null;
        for (MsgRoom msgRoom : msgRooms) {
            if (Objects.equals(msgRoom.getType(), type.getType())) {
                room = msgRoom;
            } else {
                totalRoom = msgRoom;
            }
        }

        totalRoom.setUpdateTime(null).setNoReadNum(totalRoom.getNoReadNum() - room.getNoReadNum());
        room.setUpdateTime(null).setNoReadNum(0);
        this.msgRoomDao.updateBatchById(msgRooms);
    }

    public Integer getNoReadMsgNum(Long userId, MsgRoomType msgRoomType) {
        return this.msgRoomDao.getNoReadNum(userId, msgRoomType);
    }

    public void totalMsgRead(Long userId, Integer num) {
        MsgRoom msgRoom = this.msgRoomDao.getMsgRoom(userId, MsgRoomType.TOTAL_MSG_ROOM);
        msgRoom.setUpdateTime((LocalDateTime)null).setNoReadNum(msgRoom.getNoReadNum() - num);
        this.msgRoomDao.updateById(msgRoom);
    }

    public void noReadMsgAdd(Long userId, Integer num, MsgRoomType... types) {
        List<MsgRoom> msgRooms = this.msgRoomDao.getMsgRooms(userId, types);
        for (MsgRoom msgRoom : msgRooms) {
            msgRoom.setUpdateTime(null).setNoReadNum(msgRoom.getNoReadNum() + num);
        }
        this.msgRoomDao.updateBatchById(msgRooms);
    }
}
