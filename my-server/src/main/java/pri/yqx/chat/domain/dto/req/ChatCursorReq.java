package pri.yqx.chat.domain.dto.req;



import jakarta.validation.constraints.NotNull;
import pri.yqx.common.domain.request.CursorReq;

public class ChatCursorReq extends CursorReq {
    private @NotNull Long userId;
    private @NotNull Long goodId;


    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public void setGoodId(final Long goodId) {
        this.goodId = goodId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public Long getGoodId() {
        return this.goodId;
    }
}