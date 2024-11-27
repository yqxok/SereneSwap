
package pri.yqx.msg.domain.vo;

import lombok.Data;

@Data
public class UserLoginMsgVo {
    private Long senderId;
    private Long receiverId;
    private String msg;


}
