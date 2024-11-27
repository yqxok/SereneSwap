//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.comment.domain.dto.req;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
@Data
public class SonCommentReq {
    private @NotEmpty String content;
    private @NotNull Long fatherId;
    private @NotNull Long replyId;


}
