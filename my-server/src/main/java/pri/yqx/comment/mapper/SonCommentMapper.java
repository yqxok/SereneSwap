//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.comment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import pri.yqx.comment.domain.entity.CommentSon;

@Mapper
public interface SonCommentMapper extends BaseMapper<CommentSon> {
    List<CommentSon> getCommentSons(List<Long> commentIds, Integer size);

    void plusGoodJobNum(Long commentId, int num);

    void substractGoodJobNum(Long commentId, int num);
}
