//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.good.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class CategoryReq {
    @NotNull
    private  String categoryName;
    private String pkId;


}
