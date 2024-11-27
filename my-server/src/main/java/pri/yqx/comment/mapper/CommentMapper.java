package pri.yqx.comment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import pri.yqx.comment.domain.entity.Comment;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    void plusGoodJob(Long commentId, int num);

    void substractGoodJob(Long commentId, int num);
}