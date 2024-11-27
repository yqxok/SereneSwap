//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.comment.service.event.listner;

import java.util.Objects;
import javax.annotation.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import pri.yqx.comment.dao.CommentDao;
import pri.yqx.comment.dao.SonCommentDao;
import pri.yqx.comment.domain.entity.Comment;
import pri.yqx.comment.domain.entity.CommentSon;
import pri.yqx.comment.service.event.CommentEvent;
import pri.yqx.good.domain.entity.Good;
import pri.yqx.good.service.cache.GoodCache;
import pri.yqx.msg.domain.dto.CommentMsgDto;
import pri.yqx.msg.domain.enums.CommentMsgType;
import pri.yqx.rocketmq.produce.MqProducer;

@Component
public class CommentEventListner {
    @Resource
    private MqProducer mqProducer;
    @Resource
    private CommentDao commentDao;
    @Resource
    private GoodCache goodCache;
    @Resource
    private SonCommentDao sonCommentDao;

    public CommentEventListner() {
    }

    @Async
    @TransactionalEventListener({CommentEvent.class})
    public void sendCommentMsg(CommentEvent commentEvent) {
        CommentMsgDto cmDto = new CommentMsgDto().setCommentId(commentEvent.getCommentId()).setType(commentEvent.getType());
        if (Objects.equals(cmDto.getType(), CommentMsgType.FATHER_COMMENT.getType())) {
            Comment comment = (Comment)this.commentDao.getById(cmDto.getCommentId());
            Good good = this.goodCache.getCache(comment.getGoodId());
            if (Objects.equals(comment.getUserId(), good.getUserId())) {
                return;
            }

            cmDto.setSenderId(comment.getUserId()).setReceiverId(good.getUserId()).setGoodId(comment.getGoodId()).setContent(comment.getContent());
        } else {
            CommentSon commentSon = this.sonCommentDao.getById(cmDto.getCommentId());
            Comment comment = this.commentDao.getById(commentSon.getCommentId());
            if (Objects.equals(comment.getUserId(), comment.getReplyId())) {
                return;
            }

            cmDto.setSenderId(commentSon.getUserId()).setReceiverId(commentSon.getReplyId()).setGoodId(comment.getGoodId()).setContent(commentSon.getContent());
        }

        this.mqProducer.sendMsg("comment_msg", cmDto);
    }
}
