//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.comment.domain.dto.req;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import pri.yqx.common.groups.Update;
@Data
public class CommentReq {
    @NotNull(groups = {Update.class})
    private  Long commentId;
    private @NotNull Long goodId;
    private @NotEmpty String content;


}
