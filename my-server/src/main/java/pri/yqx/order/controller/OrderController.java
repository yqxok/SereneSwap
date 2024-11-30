package pri.yqx.order.controller;

import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pri.yqx.common.common.ThreadHolder;
import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.common.domain.response.Result;
import pri.yqx.order.domain.dto.OrderCursorReq;
import pri.yqx.order.domain.dto.OrderReq;
import pri.yqx.order.domain.dto.StatusReq;
import pri.yqx.order.domain.vo.OrderDetailVo;
import pri.yqx.order.domain.vo.OrderVo;
import pri.yqx.order.service.OrderService;

@RestController
@RequestMapping({"/order"})
public class OrderController {
    @Resource
    private OrderService orderService;

    public OrderController() {
    }

    @PostMapping({"/page"})
    public Result<CursorPageVo<OrderVo>> getCursorPage(@Validated @RequestBody OrderCursorReq cursorDto) {
        CursorPageVo<OrderVo> cursorPageVo = this.orderService.getCursorPage(ThreadHolder.get(), cursorDto);
        return Result.success(cursorPageVo, "订单页面查询成功");
    }

    @GetMapping({"/{orderId}"})
    public Result<OrderDetailVo> getOrder(@PathVariable Long orderId) {
        OrderDetailVo orderDetail = this.orderService.getOrderDetail(ThreadHolder.get(), orderId);
        return Result.success(orderDetail, "订单详情查询成功");
    }

    /**
     * 修改订单状态
     * @param statusReq
     * @return
     */
    @PutMapping({"/status"})
    public Result<String> updateOrderStatus(@Validated @RequestBody StatusReq statusReq) {
        this.orderService.updateStatus(ThreadHolder.get(), statusReq);
        return Result.success(null, "订单状态修改成功");
    }

    @PostMapping
    public Result<Long> saveOrder(@Validated @RequestBody OrderReq orderReq) {
        Long orderId = this.orderService.saveOrder(ThreadHolder.get(), orderReq);
        return Result.success(orderId, "下单成功");
    }

    /**
     * 删除订单
     * @param orderId
     * @return
     */
    @DeleteMapping("/{orderId}")
    public Result<String> deleteOrder(@PathVariable Long orderId){
        orderService.deleteOrder(ThreadHolder.get(),orderId);
        return Result.success(null,"订单删除成功");
    }
}