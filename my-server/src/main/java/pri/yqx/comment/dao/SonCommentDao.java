package pri.yqx.comment.dao;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pri.yqx.comment.domain.entity.CommentSon;
import pri.yqx.comment.domain.vo.CmCursorPageVo;
import pri.yqx.comment.mapper.SonCommentMapper;
import pri.yqx.comment.service.adapter.CommentAdapter;
import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.common.util.CursorUtil;

@Component
public class SonCommentDao extends ServiceImpl<SonCommentMapper, CommentSon> {
    @Autowired
    private SonCommentMapper sonCommentMapper;

    public SonCommentDao() {
    }

    public Map<Long, CmCursorPageVo<CommentSon>> getSonCommentMap(List<Long> commentIds, Integer size) {
        List<CommentSon> list = this.sonCommentMapper.getCommentSons(commentIds, size + 1);
        return CommentAdapter.buildSonCMCursorPage(list, size);
    }

    public CursorPageVo<CommentSon> getSonCursorPage(Long commentId, Long cursor, Integer pageSize) {
        return CursorUtil.init(this, pageSize, (lambda) -> {
            lambda.eq(CommentSon::getCommentId, commentId)
                    .orderByDesc(CommentSon::getSonCommentId).le(cursor != 0L, CommentSon::getSonCommentId, cursor);
        }, CommentSon::getSonCommentId);
    }

    public void plusGoodJobNum(Long commentId, int num) {
        this.sonCommentMapper.plusGoodJobNum(commentId, num);
    }

    public void substractGoodJobNum(Long commentId, int num) {
        this.sonCommentMapper.substractGoodJobNum(commentId, num);
    }
}