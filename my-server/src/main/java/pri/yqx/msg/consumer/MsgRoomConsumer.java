package pri.yqx.msg.consumer;

import java.util.Arrays;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pri.yqx.msg.dao.MsgRoomDao;
import pri.yqx.msg.domain.entity.MsgRoom;
import pri.yqx.msg.domain.enums.MsgRoomType;
import pri.yqx.user.domain.dto.UserSigninDto;

@Component
@RocketMQMessageListener(
    topic = "user_signin",
    consumerGroup = "user_signin_group"
)
public class MsgRoomConsumer implements RocketMQListener<UserSigninDto> {
    private static final Logger log = LoggerFactory.getLogger(MsgRoomConsumer.class);
    @Autowired
    private MsgRoomDao msgRoomDao;


    @Transactional
    public void onMessage(UserSigninDto dto) {
        try {
            MsgRoom msgRoom = (new MsgRoom()).setUserId(dto.getUserId()).setType(MsgRoomType.COMMENT_ROOM.getType()).setNoReadNum(0);
            MsgRoom msgRoom1 = (new MsgRoom()).setUserId(dto.getUserId()).setType(MsgRoomType.ORDER_ROOM.getType()).setNoReadNum(0);
            MsgRoom msgRoom2 = new MsgRoom().setUserId(dto.getUserId()).setType(MsgRoomType.TOTAL_MSG_ROOM.getType()).setNoReadNum(0);
            this.msgRoomDao.saveBatch(Arrays.asList(msgRoom, msgRoom1,msgRoom2));
        } catch (Exception exp) {
            log.warn("消费出现异常,{}", dto, exp);
            throw new RuntimeException(exp);
        }
    }
}