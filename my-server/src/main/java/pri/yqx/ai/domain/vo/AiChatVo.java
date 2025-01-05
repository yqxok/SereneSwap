package pri.yqx.ai.domain.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class AiChatVo {
    private Long chatId;
    private Integer type;
    private Long roomId;
    private LocalDateTime createTime;
//    private String sendTime;
    private String content;
    private List<Goods> goods;
    @Data
    public static class Goods{
        private String url;
        private String html;
        private BigDecimal price;
        private Long goodId;
    }
}
