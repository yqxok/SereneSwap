//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.comment.domain.dto.req;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import pri.yqx.common.domain.request.CursorReq;
@Getter
@Setter
public class SonCmCurosrReq extends CursorReq {
    private @NotNull Long commentId;


}
