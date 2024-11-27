//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.user.service.cache;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import javax.annotation.Resource;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import pri.yqx.common.service.cache.AbstractMutiCache;
import pri.yqx.common.service.cache.RedisCache;
import pri.yqx.user.dao.UserDao;
import pri.yqx.user.domain.entity.User;

@Component
public class UserCache extends AbstractMutiCache<User> implements RedisCache<User> {
    @Resource
    private UserDao userDao;

    public UserCache() {
    }

    protected String getRedisKey() {
        return "userInfo";
    }

    protected Function<User, Long> getEntityId() {
        return User::getUserId;
    }

    protected Long getIdFuc(User bean) {
        return bean.getUserId();
    }

    protected IService<User> getIService() {
        return this.userDao;
    }

    public Map<Long, User> getAllCache(Set<Long> ids) {
        return this.getCacheMap(ids, (wrapper, ids1) -> {
            wrapper.in(User::getUserId, ids1);
        });
    }

    public Map<Long, User> getPhysicExitCaches(Set<Long> ids) {
        return this.getCacheMap(ids, (wrapper, ids1) -> {
         wrapper.in(User::getUserId, ids1).eq(User::getIsDeleted, false);
        });
    }

    @Cacheable(
        cacheNames = {"userInfo"},
        key = "#id"
    )
    public User getCache(Long id) {
        return (User)this.userDao.getById(id);
    }

    @Cacheable(
        cacheNames = {"userInfo"},
        key = "#id"
    )
    public User getPhysicExitCache(Long id) {
        return this.userDao.lambdaQuery().eq(User::getUserId, id).eq(User::getIsDeleted, false).one();
    }

    @CacheEvict(
        cacheNames = {"userInfo"},
        key = "#id"
    )
    public void rmCache(Long id) {
    }
}
