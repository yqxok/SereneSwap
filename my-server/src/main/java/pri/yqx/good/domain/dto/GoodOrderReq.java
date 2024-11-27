//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.good.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;
import pri.yqx.common.groups.Update;
@Data
public class GoodOrderReq {
    private @NotNull(
    groups = {Update.class}
) Long orderId;
    private @NotNull @Min(1L) Integer number;
    private @NotNull @Min(0L) BigDecimal totalPrice;
    private @NotEmpty String rAddress;
    private @NotNull @Range(
    min = 0L,
    max = 3L
) Integer status;
    private @NotNull Long goodId;
    private @NotNull Long delivererId;
    private @NotNull Long receiverId;
    private @NotEmpty String html;
    private @URL @NotEmpty String picUrl;
    private @NotNull @Min(0L) BigDecimal price;
    private @NotNull(
    groups = {Update.class}
) LocalDateTime dealTime;
    private @NotEmpty String receiver;
    private @NotEmpty String phoneNumber;


}
