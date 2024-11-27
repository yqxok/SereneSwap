package pri.yqx.common.domain.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
@Data
public class CursorReq {
    @NotNull @Min(0L)
    private  Long cursor;
    @NotNull @Range(min = 1L, max = 50L)
    private  Integer pageSize;

}