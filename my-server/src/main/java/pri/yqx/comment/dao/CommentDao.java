//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.comment.dao;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pri.yqx.comment.domain.entity.Comment;
import pri.yqx.comment.mapper.CommentMapper;
import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.common.util.CursorUtil;

@Repository
public class CommentDao extends ServiceImpl<CommentMapper, Comment> {
    @Autowired
    private CommentMapper commentMapper;

    public CommentDao() {
    }

    public CursorPageVo<Comment> getCursorPage(Long goodId, Long cursor, Integer pageSize) {
        return CursorUtil.init(this, pageSize, (lambda) -> {
            lambda.le(cursor != 0L, Comment::getCommentId, cursor)
                    .eq(Comment::getGoodId, goodId).orderByDesc(Comment::getCommentId);
        }, Comment::getCommentId);
    }

    public void plusGoodJobNum(Long commentId, int num) {
        this.commentMapper.plusGoodJob(commentId, num);
    }

    public void substractGoodJobNum(Long commentId, int num) {
        this.commentMapper.substractGoodJob(commentId, num);
    }
}
