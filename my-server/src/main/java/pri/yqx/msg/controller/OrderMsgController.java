package pri.yqx.msg.controller;

import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pri.yqx.common.common.ThreadHolder;
import pri.yqx.common.domain.request.CursorReq;
import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.common.domain.response.Result;
import pri.yqx.msg.domain.vo.OrderMsgRoomVo;
import pri.yqx.msg.domain.vo.OrderMsgVo;
import pri.yqx.msg.service.OrderMsgService;

@RestController
@RequestMapping({"/orderMsg"})
public class OrderMsgController {
    @Resource
    private OrderMsgService orderMsgService;

    public OrderMsgController() {
    }

    @PostMapping({"/page"})
    public Result<CursorPageVo<OrderMsgVo>> cursorPage(@RequestBody @Validated CursorReq cursorReq) {
        CursorPageVo<OrderMsgVo> cursorPage = this.orderMsgService.getCursorPage(ThreadHolder.get(), cursorReq);
        return Result.success(cursorPage, "订单消息查询成功");
    }

    @GetMapping({"/room"})
    public Result<OrderMsgRoomVo> getOrderMsgRoom() {
        OrderMsgRoomVo orderMsgRoomVo = this.orderMsgService.getOrderMsgRoomVo(ThreadHolder.get());
        return Result.success(orderMsgRoomVo, "订单消息房间查询成功");
    }
}