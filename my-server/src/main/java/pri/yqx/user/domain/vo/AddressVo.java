//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.user.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AddressVo {
    private Long addressId;
    private Integer dormiNum;
    private DormitoryInfo dormiInfo;
    private String phoneNumber;
    private String receiver;
    private Boolean isDefault;


    @Data
    public static class DormitoryInfo {
        private Long dormitoryId;
        private String dormiName;
        private String school;
        private String zone;


    }
}
