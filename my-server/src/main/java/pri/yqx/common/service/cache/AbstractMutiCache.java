//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.common.service.cache;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import pri.yqx.common.constant.RedisKey;
import pri.yqx.common.util.RedisUtil;

public abstract class AbstractMutiCache<T> implements MutiCache<T> {
    private Class<T> outClass;
    private static final int LAST_TIME = 1800;

    protected AbstractMutiCache() {
        ParameterizedType genericSuperclass = (ParameterizedType)this.getClass().getGenericSuperclass();
        this.outClass = (Class)genericSuperclass.getActualTypeArguments()[0];
    }

    protected abstract String getRedisKey();

    protected abstract Function<T, Long> getEntityId();

    protected abstract Long getIdFuc(T bean);

    public Map<Long, T> getCacheMap(Set<Long> ids, QueryHandler<T> handler) {
        if (CollectionUtil.isEmpty(ids)) {
            return new HashMap<>();
        } else {
            List<String> keys = ids.stream().map((i) -> {
                return RedisKey.getRedisKey(this.getRedisKey(), i);
            }).collect(Collectors.toList());
            List<T> list = RedisUtil.mutiGet(keys, this.outClass);
            Map<Long, T> collect = list.stream().filter(Objects::nonNull).collect(Collectors.toMap(this.getEntityId(), Function.identity()));
            List<Long> nofitIds = ids.stream().filter((i) -> {
                return !collect.containsKey(i);
            }).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(nofitIds)) {
                LambdaQueryWrapper<T> wrapper = new LambdaQueryWrapper<>();
                handler.handle(wrapper, nofitIds);
                List<T> entities = this.getIService().list(wrapper);
                Map<String, T> noFitMap = entities.stream().collect(Collectors.toMap((i) -> {
                    return RedisKey.getRedisKey(this.getRedisKey(), this.getIdFuc(i));
                }, Function.identity()));
                RedisUtil.mutiSet(noFitMap, 1800L);
                collect.putAll(entities.stream().collect(Collectors.toMap(this.getEntityId(), Function.identity())));
            }

            return collect;
        }
    }

    protected abstract IService<T> getIService();
}
