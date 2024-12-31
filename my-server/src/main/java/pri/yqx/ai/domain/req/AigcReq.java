package pri.yqx.ai.domain.req;

import lombok.Data;

import java.util.List;

@Data
public class AigcReq {
    private String message;
    private List<History> history;
    @Data
    public static class History{
        private Integer type;
        private String content;
        private String id;
    }

}
