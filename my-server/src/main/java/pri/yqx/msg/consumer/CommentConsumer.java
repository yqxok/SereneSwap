//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.msg.consumer;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pri.yqx.msg.dao.CommentMsgDao;
import pri.yqx.msg.domain.dto.CommentMsgDto;
import pri.yqx.msg.domain.enums.MsgRoomType;
import pri.yqx.msg.domain.vo.CommentMsgVo;
import pri.yqx.msg.service.CommentMsgService;
import pri.yqx.msg.service.MsgRoomService;
import pri.yqx.websocket.service.WebSocketService;
import pri.yqx.websocket.service.WsMsgType;

@Component
@RocketMQMessageListener(
    consumerGroup = "comment_group",
    topic = "comment_msg"
)
@Slf4j
public class CommentConsumer implements RocketMQListener<CommentMsgDto> {

    @Resource
    private CommentMsgDao commentMsgDao;
    @Resource
    private CommentMsgService commentMsgService;
    @Resource
    private WebSocketService webSocketService;
    @Resource
    private MsgRoomService msgRoomService;


    public void onMessage(CommentMsgDto cmDto) {
        if (this.commentMsgDao.isExit(cmDto.getCommentId())) {
            log.warn("消息被重复消费了,{}", cmDto);
        } else {
            log.info("我收到消息了,{}", cmDto);
            this.commentMsgService.saveCommmentMsg(cmDto);

            CommentMsgVo msgVo = this.commentMsgService.getCommentMsgVo(cmDto.getCommentId());
            this.webSocketService.sendMsg(cmDto.getReceiverId(), msgVo, WsMsgType.COMMENT_MSG);
        }
    }
}
