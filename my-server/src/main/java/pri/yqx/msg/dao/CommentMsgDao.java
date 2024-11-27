package pri.yqx.msg.dao;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.Objects;
import org.springframework.stereotype.Component;
import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.common.util.CursorUtil;
import pri.yqx.msg.domain.entity.CommentMsg;
import pri.yqx.msg.mapper.CommentMsgMapper;

@Component
public class CommentMsgDao extends ServiceImpl<CommentMsgMapper, CommentMsg> {
    public CommentMsgDao() {
    }

    public CommentMsg latestMsg(Long userId) {
        Page<CommentMsg> page = this.lambdaQuery().eq(CommentMsg::getReceiverId, userId)
                .orderByDesc(CommentMsg::getCreateTime).page(new Page<>(1L, 1L));
        return !Objects.isNull(page.getRecords()) && !page.getRecords().isEmpty() ? (CommentMsg)page.getRecords().get(0) : null;
    }

    public CommentMsg getByCommentId(Long commentId) {
        return this.lambdaQuery().eq(CommentMsg::getCommentId, commentId).one();
    }

    public boolean isExit(Long commentId) {
        return this.lambdaQuery().eq(CommentMsg::getCommentId, commentId).count() > 0L;
    }

    public CursorPageVo<CommentMsg> getCursorPage(Long userId, Long cursor, Integer pageSize) {
        return CursorUtil.init(this, pageSize, (lambda) -> {
            lambda.orderByDesc(CommentMsg::getCmMsgId)
                    .eq(CommentMsg::getReceiverId, userId)
                    .le(cursor != 0L, CommentMsg::getCmMsgId, cursor).eq(CommentMsg::getIsDeleted, false);
        }, CommentMsg::getCmMsgId);
    }
}