package pri.yqx.msg.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pri.yqx.common.common.ThreadHolder;
import pri.yqx.common.domain.response.Result;
import pri.yqx.msg.domain.dto.req.MsgTypeReq;
import pri.yqx.msg.domain.enums.MsgRoomType;
import pri.yqx.msg.service.MsgRoomService;

@RestController
@RequestMapping({"/msgRoom"})
@Validated
public class MsgRoomController {
    @Autowired
    private MsgRoomService msgRoomService;


    @PutMapping
    public Result<String> msgRead(@RequestBody @Validated MsgTypeReq msgTypeReq) {
        this.msgRoomService.msgRead(ThreadHolder.get(), msgTypeReq);
        return Result.success(null, "消息已读成功");
    }

    @GetMapping
    public Result<Integer> getNoReadMsgNum() {
        Integer num = this.msgRoomService.getNoReadMsgNum(ThreadHolder.get(), MsgRoomType.TOTAL_MSG_ROOM);
        return Result.success(num, "未读消息获取成功");
    }
}