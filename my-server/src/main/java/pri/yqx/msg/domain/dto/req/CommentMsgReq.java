//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.msg.domain.dto.req;



import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import pri.yqx.common.groups.Update;
@Data
public class CommentMsgReq {
    private @NotNull(
    groups = {Update.class}
) Long commentId;
    private @NotNull Long goodId;
    private @NotEmpty String content;
    private @Range(
    min = 0L,
    max = 1L
) @NotNull Short type;
    private @NotNull Long senderId;
    private @NotNull Long receiverId;


}
