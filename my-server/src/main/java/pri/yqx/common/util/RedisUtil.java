//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.common.util;

import cn.hutool.extra.spring.SpringUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisUtil {
    private static RedisTemplate<String, Object> redisTemplate = (RedisTemplate)SpringUtil.getBean("redisTemplate");

    public RedisUtil() {
    }

    public static <T> T get(String key, Class<T> clazz) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    public static <T> void set(String key, T value, long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    public static <T> List<T> mutiGet(List<String> ids, Class<T> clazz) {
        List<Object> values = redisTemplate.opsForValue().multiGet(ids);
        return (List)(Objects.isNull(values) ? new ArrayList() : (List)values.stream().map((i) -> {
            return i;
        }).collect(Collectors.toList()));
    }

    public static void expire(String key, long time) {
        redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    public static <T> void mutiSet(Map<String, T> map, long time) {
        redisTemplate.opsForValue().multiSet(map);
        map.forEach((key, value) -> {
            expire(key, time);
        });
    }

    public static void deleteKeys(Set<String> keys) {
        redisTemplate.delete(keys);
    }

    public static void deleteKey(String key) {
        redisTemplate.delete(key);
    }
}
