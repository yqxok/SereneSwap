package pri.yqx.ai.domain.req;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AiChatReq {
    @NotNull
    private String userContent;
    @NotNull
    private String aiContent;
    private String goods="[]";
    @NotNull
    private Long roomId;

}
