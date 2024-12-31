//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.good.service.cache;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import pri.yqx.common.constant.RedisKey;
import pri.yqx.common.service.cache.AbstractMutiCache;
import pri.yqx.common.service.cache.RedisCache;
import pri.yqx.good.dao.GoodCatogryDao;
import pri.yqx.good.dao.GoodDao;
import pri.yqx.good.domain.entity.Good;
import pri.yqx.good.domain.enums.GoodStatusEnums;

@Component
public class GoodCache extends AbstractMutiCache<Good> implements RedisCache<Good> {
    @Autowired
    private GoodDao goodDao;

    protected String getRedisKey() {
        return "good_info";
    }

    protected Function<Good, Long> getEntityId() {
        return Good::getGoodId;
    }

    protected Long getIdFuc(Good bean) {
        return bean.getGoodId();
    }

    protected IService<Good> getIService() {
        return this.goodDao;
    }

    public Map<Long, Good> getAllCache(Set<Long> ids) {
        return this.getCacheMap(ids, (wrapper, collection) -> {
            wrapper.in(Good::getGoodId, collection);
        });
    }

    public Map<Long, Good> getPhysicExitCaches(Set<Long> ids) {
        return this.getCacheMap(ids, (wrapper, collection) -> {
            wrapper.in(Good::getGoodId, collection).eq(Good::getStatus, GoodStatusEnums.UNSELL.getStatus()).eq(Good::getIsDeleted, false);
        });
    }

    @Cacheable(value = RedisKey.BASE_KEY+ RedisKey.GOOD_INFO, key = "#id")
    public Good getCache(Long id) {
        return this.goodDao.getById(id);
    }

    @Cacheable(value = RedisKey.BASE_KEY+ RedisKey.GOOD_INFO, key = "#id")
    public Good getPhysicExitCache(Long id) {
        return this.goodDao.lambdaQuery().eq(Good::getGoodId, id).eq(Good::getStatus, GoodStatusEnums.UNSELL.getStatus())
                .eq(Good::getIsDeleted, false).one();
    }

    @CacheEvict(value = RedisKey.BASE_KEY+ RedisKey.GOOD_INFO, key = "#id")
    public void rmCache(Long id) {
    }
}
