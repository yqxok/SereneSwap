
package pri.yqx.msg.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CommentMsgDto {
    private Long commentId;
    private Integer type;
    private String content;
    private Long goodId;
    private Long receiverId;
    private Long senderId;


}
