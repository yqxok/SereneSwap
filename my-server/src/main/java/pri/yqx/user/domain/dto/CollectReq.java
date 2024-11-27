//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.user.domain.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;
import pri.yqx.common.groups.Insert;
import pri.yqx.common.groups.Update;
@Data
public class CollectReq {
    private @NotNull(
    groups = {Update.class}
) Long collectId;
    private @NotNull(
    groups = {Insert.class}
) Long goodId;


}
