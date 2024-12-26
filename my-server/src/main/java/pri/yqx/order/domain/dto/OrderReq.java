//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.order.domain.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderReq {
    private @NotNull Long goodId;
    private @NotNull Long addressId;
    private @NotNull @Min(1L) Integer buyNum;


}
