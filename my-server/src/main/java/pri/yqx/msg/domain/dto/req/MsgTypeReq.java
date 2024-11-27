//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.msg.domain.dto.req;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
@Data
public class MsgTypeReq {
    @Range(min = 0L, max = 1L)
    private Integer type;


}
