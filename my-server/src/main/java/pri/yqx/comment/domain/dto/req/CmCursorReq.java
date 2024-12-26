package pri.yqx.comment.domain.dto.req;



import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import pri.yqx.common.domain.request.CursorReq;
@Getter
@Setter
public class CmCursorReq extends CursorReq {
    @NotNull
    private Long goodId;


}