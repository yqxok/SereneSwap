//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.good.domain.vo;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;
import pri.yqx.good.domain.json.PicUrl;
@Data
public class GoodDetailVo {
    private Long goodId;
    private String html;
    private List<PicUrl> picUrls;
    private BigDecimal price;
    private Integer status;
    private Integer goodNum;
    private Integer collectNum;
    private GoodVo.UserInfo userInfo;
    private List<String> categories;


}
