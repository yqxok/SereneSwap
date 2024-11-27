package pri.yqx.msg.consumer;

import java.util.Arrays;
import javax.annotation.Resource;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    @Resource
    private MsgRoomDao msgRoomDao;

    public MsgRoomConsumer() {
    }

    @Transactional
    public void onMessage(UserSigninDto dto) {
        try {
            MsgRoom msgRoom = (new MsgRoom()).setUserId(dto.getUserId()).setType(MsgRoomType.COMMENT_ROOM.getType()).setNoReadNum(0);
            MsgRoom msgRoom1 = (new MsgRoom()).setUserId(dto.getUserId()).setType(MsgRoomType.ORDER_ROOM.getType()).setNoReadNum(0);
            this.msgRoomDao.saveBatch(Arrays.asList(msgRoom, msgRoom1));
        } catch (Exception var4) {
            log.warn("消费出现异常,{}", dto, var4);
            throw new RuntimeException(var4);
        }
    }
}