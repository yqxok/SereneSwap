package pri.yqx.common.service.cache;

import java.util.Map;
import java.util.Set;

public interface RedisCache<T> {
    Map<Long, T> getAllCache(Set<Long> ids);

    Map<Long, T> getPhysicExitCaches(Set<Long> ids);
}
