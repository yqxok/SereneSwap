//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.order.domain.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
public class OrderDetailVo extends OrderVo {
    private AddressInfo addressInfo;
    @Data
    @Accessors(chain = true)
    public static class AddressInfo {
        private String address;
        private String phoneNumber;
        private String receiver;


    }
}
