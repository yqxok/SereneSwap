//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.good.domain.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
import pri.yqx.common.groups.Insert;
@Data
public class DormitoryReq {
    @NotEmpty(groups = {Insert.class})
    private  String dormiName;
    @NotEmpty(groups = {Insert.class})
    private  String school;
    @NotEmpty(groups = {Insert.class})
    private  String zone;


}
