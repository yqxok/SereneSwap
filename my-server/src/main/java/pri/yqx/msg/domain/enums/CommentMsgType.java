package pri.yqx.msg.domain.enums;

public enum CommentMsgType {
    FATHER_COMMENT(0, "在您的宝贝下留言了"),
    SON_COMMENT(1, "回复了你");

    private final Integer type;
    private final String msg;

    private CommentMsgType(Integer type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public Integer getType() {
        return this.type;
    }

    public String getMsg() {
        return this.msg;
    }
}