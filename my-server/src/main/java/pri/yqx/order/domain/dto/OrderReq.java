//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.order.domain.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
@Data
public class OrderReq {
    private @NotNull Long goodId;
    private @NotNull Long addressId;
    private @NotNull @Min(1L) Integer buyNum;


}
