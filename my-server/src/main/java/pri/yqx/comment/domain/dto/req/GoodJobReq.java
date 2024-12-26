//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.comment.domain.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GoodJobReq {
    private @NotNull Long gjId;
    private @NotNull Long commentId;
    private @NotNull Long userId;


}
