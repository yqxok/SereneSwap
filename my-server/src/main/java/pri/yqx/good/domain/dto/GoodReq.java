//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.good.domain.dto;

import java.math.BigDecimal;
import java.util.List;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import pri.yqx.common.groups.Insert;
import pri.yqx.common.groups.Update;
import pri.yqx.good.domain.json.PicUrl;
@Data
public class GoodReq {
    @NotNull(groups = {Update.class})
    private  Long goodId;
    @Length(min = 10, message = "不能小于{min}个字符")
    @NotEmpty
    private  String html;
    @Size(min = 1, message = "商品图片不能为空")
    @NotNull(groups = {Insert.class})
    private  List<PicUrl> picUrls;
    private @NotNull(
    groups = {Insert.class}
) BigDecimal price;
    private @NotNull(
    groups = {Insert.class}
) Integer goodNum;
    private @NotNull(
    groups = {Insert.class}
) List<String> categories;


}
