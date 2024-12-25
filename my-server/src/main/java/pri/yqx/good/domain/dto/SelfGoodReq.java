package pri.yqx.good.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SelfGoodReq {
    @NotNull
    private Short status;
    @NotNull
    private Integer DeviceWidth;
    @NotNull
    private Integer maxHeight;//前端图片展示的最大高度
}
