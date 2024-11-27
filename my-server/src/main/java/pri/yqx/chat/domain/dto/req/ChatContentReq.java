//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.chat.domain.dto.req;

import lombok.Data;
import pri.yqx.common.groups.Insert;
import pri.yqx.common.groups.Update;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ChatContentReq {
    @NotNull(groups = {Update.class})
    private  Long chatId;
    private Long sendUserId;
    @NotNull(groups = {Insert.class})
    private  Long receiveUserId;
    @NotEmpty(groups = {Insert.class})
    private  String content;
    private Boolean isRead = false;
    @NotNull
    private Long goodId;


}
