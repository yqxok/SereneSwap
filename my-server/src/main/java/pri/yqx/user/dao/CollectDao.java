
package pri.yqx.user.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import org.springframework.stereotype.Repository;
import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.common.util.CursorUtil;
import pri.yqx.user.domain.entity.Collect;
import pri.yqx.user.domain.entity.User;
import pri.yqx.user.mapper.CollectMapper;

@Repository
public class CollectDao extends ServiceImpl<CollectMapper, Collect> {


    public CursorPageVo<Collect> getCursorPage(Long cursor, Integer pageSize, Long userId) {
        return CursorUtil.init(this, pageSize, (lambda) -> {
           lambda.eq(Collect::getUserId, userId).le(cursor != 0L, Collect::getCollectId, cursor).orderByDesc(Collect::getCollectId);
        }, Collect::getCollectId);
    }

    public Boolean isCollected(Long userId, Long goodId) {
        Long count = this.lambdaQuery().eq(Collect::getUserId, userId).eq(Collect::getGoodId, goodId).count();
        return count != 0L;
    }

    public void deleteList(List<Long> goodIds, Long userId) {

        this.remove(new LambdaQueryWrapper<Collect>().eq(Collect::getUserId, userId).in(Collect::getGoodId, goodIds));
    }
}