package pri.yqx.msg.domain.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum MsgRoomType {
    COMMENT_ROOM(0),
    ORDER_ROOM(1),
    TOTAL_MSG_ROOM(2);

    private final Integer type;
    private static Map<Integer, MsgRoomType> cache = (Map)Arrays.stream(values()).collect(Collectors.toMap(MsgRoomType::getType, Function.identity()));

    private MsgRoomType(Integer type) {
        this.type = type;
    }

    public static MsgRoomType getMsgRoomType(Integer type) {
        return (MsgRoomType)cache.get(type);
    }

    public Integer getType() {
        return this.type;
    }
}