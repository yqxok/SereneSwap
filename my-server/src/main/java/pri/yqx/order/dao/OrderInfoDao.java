package pri.yqx.order.dao;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;
import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.common.util.CursorUtil;
import pri.yqx.order.domain.dto.OrderCursorReq;
import pri.yqx.order.domain.entity.OrderInfo;
import pri.yqx.order.mapper.OrderInfoMapper;

@Component
public class OrderInfoDao extends ServiceImpl<OrderInfoMapper, OrderInfo> {
    public OrderInfoDao() {
    }

    public CursorPageVo<OrderInfo> getCursorPage(Long userId, OrderCursorReq c) {
        return CursorUtil.init(this, c.getPageSize(), (lambada) -> {
         lambada.eq(OrderInfo::getIsBuyer, c.getIsBuyer()).eq(OrderInfo::getUserId, userId)
                 .le(c.getCursor() != 0L, OrderInfo::getOrderInfoId, c.getCursor()).orderByDesc(OrderInfo::getOrderInfoId);
        }, OrderInfo::getOrderInfoId);
    }

    public OrderInfo getOrderInfo(Long userId, Long orderId) {
        return this.lambdaQuery().eq(OrderInfo::getUserId, userId).eq(OrderInfo::getOrderId, orderId).one();
    }

    public OrderInfo getOrderInfo(Long orderId, Boolean isBuyer) {
        return this.lambdaQuery().eq(OrderInfo::getOrderId, orderId).eq(OrderInfo::getIsBuyer, isBuyer).one();
    }
}