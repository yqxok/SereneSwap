package pri.yqx.good.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pri.yqx.common.domain.request.CursorReq;
import pri.yqx.good.domain.enums.GoodStatusEnums;


@Getter
@Setter
@Accessors(chain = true)
public class GoodCursorReq extends CursorReq {
    private String categoryName;
    private Integer leftHeight=0;
    private Integer rightHeight=0;
    @NotNull
    private Integer DeviceWidth;
    @NotNull
    private Integer maxHeight;//前端图片展示的最大高度

}
