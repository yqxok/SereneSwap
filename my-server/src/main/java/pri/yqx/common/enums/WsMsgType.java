package pri.yqx.common.enums;

public enum WsMsgType {
    CHAT("/chat"),
    ORDER("/order"),
    COMMENT("/comment");

    private String value;

    private WsMsgType(String value) {
        this.value = value;
    }
}