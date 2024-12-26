//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.comment.domain.dto.req;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class SonCommentReq {
    private @NotEmpty String content;
    private @NotNull Long fatherId;
    private @NotNull Long replyId;


}
