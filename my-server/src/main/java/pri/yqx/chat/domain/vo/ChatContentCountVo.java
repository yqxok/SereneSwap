

package pri.yqx.chat.domain.vo;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;
import pri.yqx.good.domain.json.PicUrl;
@Data
@Accessors(chain = true)
public class ChatContentCountVo {
    private Integer noReadCount;
    private Long theOtherId;
    private String content;
    private LocalDateTime createTime;
    private String theOtherName;
    private String theOtherProfileImg;
    private Long goodId;
    private List<PicUrl> picUrls;

}
