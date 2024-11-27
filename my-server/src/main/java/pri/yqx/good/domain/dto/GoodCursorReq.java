package pri.yqx.good.domain.dto;

import lombok.Getter;
import lombok.Setter;
import pri.yqx.common.domain.request.CursorReq;
@Getter
@Setter
public class GoodCursorReq extends CursorReq {
    private String categoryName;


}
