package pri.yqx.good.domain.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 用户主页商品展示
 */
@Getter
@Setter
@Accessors(chain = true)
public class GoodCollectVo {
    private List<GoodVo> list1;
    private List<GoodVo> list2;
}
