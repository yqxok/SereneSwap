//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.user.domain.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import pri.yqx.common.groups.Insert;
import pri.yqx.common.groups.Isphone;
import pri.yqx.common.groups.Update;
@Data
public class AddressReq {
    @NotNull(groups = {Update.class})
    private  Long addressId;
    @NotEmpty(groups = {Insert.class})
    private  String receiver;
    @Isphone(groups = {Insert.class})
    @NotEmpty(groups = {Insert.class})
    private String phoneNumber;
    private Long dormitoryId;
    @NotNull(groups = {Insert.class})
    private Integer dormiNum;
    @Data
    public static class DefaultAdrDto {
        private Long addressId;
    }

}
