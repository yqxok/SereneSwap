package pri.yqx.order.service;

import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.order.domain.dto.OrderCursorReq;
import pri.yqx.order.domain.dto.OrderReq;
import pri.yqx.order.domain.dto.StatusReq;
import pri.yqx.order.domain.entity.OrderUnion;
import pri.yqx.order.domain.vo.OrderDetailVo;
import pri.yqx.order.domain.vo.OrderVo;

public interface OrderService {
    CursorPageVo<OrderVo> getCursorPage(Long userId, OrderCursorReq cursorDto);

    OrderDetailVo getOrderDetail(Long userId, Long orderId);

    void updateStatus(Long userId, StatusReq statusDto);

    Long saveOrder(Long userId, OrderReq orderReq);

    OrderUnion getOrder(Long userId, Long orderId);

    void deleteOrder(Long aLong, Long orderId);
}