package pri.yqx.chat.controller;

import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pri.yqx.chat.domain.dto.req.ChatContentReq;
import pri.yqx.chat.domain.dto.req.ChatCursorReq;
import pri.yqx.chat.domain.vo.ChatContentVo;
import pri.yqx.chat.service.ChatContentService;
import pri.yqx.common.common.ThreadHolder;
import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.common.domain.response.Result;

@RestController
@RequestMapping({"/chatContent"})
public class ChatContentController {
    @Resource
    private ChatContentService chatContentService;

    public ChatContentController() {
    }

    @PutMapping({"/{goodId}/{otherId}"})
    public Result<String> chatContentRead(@PathVariable Long goodId, @PathVariable Long otherId) {
        this.chatContentService.updateContentRead(ThreadHolder.get(), goodId, otherId);
        return Result.success(null, "消息已读状态修改成功");
    }

    @PostMapping({"/records"})
    public Result<CursorPageVo<ChatContentVo>> getChatContetnList(@Validated @RequestBody ChatCursorReq gDto) {
        CursorPageVo<ChatContentVo> cursorPage = this.chatContentService.getCursorPage(ThreadHolder.get(), gDto);
        return Result.success(cursorPage, "聊天记录查询成功");
    }

    @PostMapping
    public Result<ChatContentVo> sendMsg(@RequestBody @Validated ChatContentReq chatContentDto) {
        Long id = this.chatContentService.sendMsg(ThreadHolder.get(), chatContentDto);
        return Result.success(this.chatContentService.getChatContent(id), "消息发送成功");
    }
}