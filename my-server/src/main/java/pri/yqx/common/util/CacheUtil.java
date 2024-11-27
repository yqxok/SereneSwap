package pri.yqx.common.util;

import java.util.Objects;
import java.util.function.Supplier;

public class CacheUtil {
    private static final int LAST_TIME = 1800;

    public CacheUtil() {
    }

    public static <R> R getCache(Supplier<R> supplier, Class<R> clazz, String redisKey, int lastTime) {
        R res = RedisUtil.get(redisKey, clazz);
        if (Objects.isNull(res)) {
            res = supplier.get();
            RedisUtil.set(redisKey, res, (long)lastTime);
        }

        return res;
    }

    public static <R> R getCache(Supplier<R> supplier, Class<R> clazz, String redisKey) {
        return getCache(supplier, clazz, redisKey, 1800);
    }
}
