package pri.yqx.websocket;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

public class NettyUtil {
    public static AttributeKey<String> TOKEN = AttributeKey.valueOf("token");

    public NettyUtil() {
    }

    public static <T> void setAttr(Channel channel, AttributeKey<T> key, T data) {
        channel.attr(key).set(data);
    }

    public static <T> T getAttr(Channel channel, AttributeKey<T> key) {
        return channel.attr(key).get();
    }
}