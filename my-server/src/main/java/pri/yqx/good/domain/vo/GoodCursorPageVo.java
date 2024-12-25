package pri.yqx.good.domain.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Service;
import pri.yqx.common.domain.response.CursorPageVo;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class GoodCursorPageVo extends CursorPageVo<GoodVo> {
    private Integer leftHeight=0;
    private Integer rightHeight=0;
    private List<GoodVo> anotherList;
}
