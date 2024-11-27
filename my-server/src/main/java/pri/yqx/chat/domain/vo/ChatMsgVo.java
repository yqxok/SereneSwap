package pri.yqx.chat.domain.vo;

public class ChatMsgVo extends ChatContentVo {
    private Long goodId;

    public ChatMsgVo() {
    }

    public Long getGoodId() {
        return this.goodId;
    }

    public void setGoodId(final Long goodId) {
        this.goodId = goodId;
    }
}
