package pri.yqx.comment.domain.enums;

public enum CommentType {
    SON_COMMENT(0),
    FATHER_COMMENT(1);

    private final Integer type;

    private CommentType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return this.type;
    }
}