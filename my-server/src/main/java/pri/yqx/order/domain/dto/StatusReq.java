//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.order.domain.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
@Data
public class StatusReq {
    private @NotNull Long orderId;
    private @NotNull @Range(
    min = 1L,
    max = 2L
) Integer status;


}
