package pri.yqx.websocket.service;

public enum WsMsgType {
    HEART_BEAT(0),
    ORDER_MSG(1),
    COMMENT_MSG(2),
    CHAT_MSG(3);

    private final Integer value;

    private WsMsgType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }
}