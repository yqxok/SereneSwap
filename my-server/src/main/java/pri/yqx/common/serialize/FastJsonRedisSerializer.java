package pri.yqx.common.serialize;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.nio.charset.Charset;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    private Class<T> clazz;

    public FastJsonRedisSerializer(Class<T> clazz) {
        this.clazz = clazz;
    }

    public byte[] serialize(T t) throws SerializationException {
        return null == t ? new byte[0] : JSON.toJSONString(t, new SerializerFeature[]{SerializerFeature.WriteClassName}).getBytes(DEFAULT_CHARSET);
    }

    public T deserialize(byte[] bytes) throws SerializationException {
        if (null != bytes && bytes.length > 0) {
            String str = new String(bytes, DEFAULT_CHARSET);
            return (T) JSON.parse(str, Feature.SupportAutoType);
        } else {
            return null;
        }
    }
}