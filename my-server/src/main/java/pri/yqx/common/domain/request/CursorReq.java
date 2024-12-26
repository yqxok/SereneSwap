package pri.yqx.common.domain.request;



import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
@Data
public class CursorReq {
    @NotNull
    @Min(0L)
    private  Long cursor;
    @NotNull @Range(min = 1L, max = 50L)
    private  Integer pageSize;

}