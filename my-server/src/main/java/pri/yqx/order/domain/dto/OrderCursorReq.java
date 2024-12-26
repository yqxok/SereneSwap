package pri.yqx.order.domain.dto;



import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import pri.yqx.common.domain.request.CursorReq;
@Data
public class OrderCursorReq extends CursorReq {
    private @NotNull Boolean isBuyer;
    private @Range(
    min = -1L,
    max = 2L
) Integer status = -1;


}