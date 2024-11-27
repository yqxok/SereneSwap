//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.comment.dao;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import pri.yqx.comment.domain.entity.GoodJob;
import pri.yqx.comment.mapper.GoodJobMapper;
import pri.yqx.common.util.MyBeanUtils;

@Repository
public class GoodJobDao extends ServiceImpl<GoodJobMapper, GoodJob> {
    public GoodJobDao() {
    }

    public Boolean isGoodJob(Long userId, Long commentId) {
        return this.lambdaQuery().eq(userId != null, GoodJob::getUserId, userId)
                .eq(commentId != null, GoodJob::getCommentId, commentId).count() > 0;
    }

    public Map<Long, GoodJob> getGoodJobs(Set<Long> commentIds, Long userId) {
        List<GoodJob> list = this.lambdaQuery().eq(userId != null, GoodJob::getUserId, userId)
                .in(commentIds != null && !commentIds.isEmpty(), GoodJob::getCommentId, commentIds).list();
        return list.stream().collect(Collectors.toMap(GoodJob::getCommentId, Function.identity()));
    }

    public Map<Long, GoodJob> isGoodJob(Long userId, Collection<Long> commentId) {
        List<GoodJob> list = this.lambdaQuery().eq(GoodJob::getUserId, userId).in(GoodJob::getCommentId, commentId).list();
        return MyBeanUtils.transMap(new HashSet<>(list), GoodJob::getCommentId);
    }

    public GoodJob getGoodJob(Long userId, Long commentId) {
        return this.lambdaQuery().eq(GoodJob::getUserId, userId).eq(GoodJob::getCommentId, commentId).one();
    }
}
