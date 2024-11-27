//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.good.dao;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Repository;
import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.common.util.CursorUtil;
import pri.yqx.good.domain.entity.Good;
import pri.yqx.good.domain.enums.GoodStatusEnums;
import pri.yqx.good.mapper.GoodMapper;

@Repository
public class GoodDao extends ServiceImpl<GoodMapper, Good> {
    public GoodDao() {
    }

    public CursorPageVo<Good> getCursorPage(Long cursor, Integer pageSize) {
        return CursorUtil.init(this, pageSize, (lambda) -> {
           lambda.le(cursor != 0L, Good::getGoodId, cursor).eq(Good::getStatus, GoodStatusEnums.UNSELL.getStatus())
                   .orderByDesc(Good::getGoodId).eq(Good::getIsDeleted, false);
        }, Good::getGoodId);
    }

    public List<Good> getGoodsByUserId(Long userId, Short status) {
        return this.lambdaQuery().eq(Good::getUserId, userId).eq(Good::getIsDeleted, false)
                .eq(Good::getStatus, status).orderByDesc(Good::getGoodId).list();
    }

    public Long isGoodExit(Long goodId, Long userId) {
        return this.lambdaQuery().eq(Good::getGoodId, goodId).eq(Good::getUserId, userId).count();
    }

    public void substractCollectNum(Collection<Long> goodIds, Long userId) {
        this.lambdaUpdate().eq(Good::getUserId, userId).in(Good::getGoodId, goodIds).setSql("collect_num=collect_num-1").update();
    }

    public void plusCollectNum(Long goodId, Long userId) {
        this.lambdaUpdate().eq(Good::getUserId, userId).in(Good::getGoodId, new Object[]{goodId}).setSql("collect_num=collect_num+1").update();
    }

    public void logicRemove(Long goodId) {
        this.lambdaUpdate().eq(Good::getGoodId, goodId).set(Good::getIsDeleted, true).update();
    }
}
