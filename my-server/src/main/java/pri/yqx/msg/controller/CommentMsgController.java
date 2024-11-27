package pri.yqx.msg.controller;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pri.yqx.common.common.ThreadHolder;
import pri.yqx.common.domain.request.CursorReq;
import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.common.domain.response.Result;
import pri.yqx.msg.domain.vo.CmMsgRoomtVo;
import pri.yqx.msg.domain.vo.CommentMsgVo;
import pri.yqx.msg.service.CommentMsgService;

@RestController
@RequestMapping({"/cmMsg"})
public class CommentMsgController {
    @Resource
    private CommentMsgService commentMsgService;

    public CommentMsgController() {
    }

    @PostMapping({"/page"})
    public Result<CursorPageVo<CommentMsgVo>> getCmMsgVoList(@RequestBody CursorReq cursorReq) {
        CursorPageVo<CommentMsgVo> cursorPageVo = this.commentMsgService.getCursorPage(ThreadHolder.get(), cursorReq);
        return Result.success(cursorPageVo, "回复消息查询成功");
    }

    @GetMapping({"/room"})
    public Result<CmMsgRoomtVo> getCmMsgRoom() {
        CmMsgRoomtVo cmMsgRoomtVo = this.commentMsgService.getCmMsgRoom(ThreadHolder.get());
        return Result.success(cmMsgRoomtVo, "互动消息房间查询完毕");
    }
}