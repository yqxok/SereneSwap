package pri.yqx.good.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;



@Data
public class SelfGoodReq {
    @NotNull
    private Short status;
    @NotNull
    private Integer DeviceWidth;
    @NotNull
    private Integer maxHeight;//前端图片展示的最大高度
}
