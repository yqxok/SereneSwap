package pri.yqx.msg.dao;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;
import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.common.util.CursorUtil;
import pri.yqx.msg.domain.entity.OrderMsg;
import pri.yqx.msg.mapper.OrderMsgMapper;

@Component
public class OrderMsgDao extends ServiceImpl<OrderMsgMapper, OrderMsg> {
    public OrderMsgDao() {
    }

    public Boolean isExit(Long orderId, Integer status) {
        return this.lambdaQuery().eq(OrderMsg::getOrderId, orderId).eq(OrderMsg::getStatus, status).count() > 0L;
    }

    public CursorPageVo<OrderMsg> getCursorPage(Long userId, Long cursor, Integer pageSize) {
        return CursorUtil.init(this, pageSize, (lambda) -> {
           lambda.eq(OrderMsg::getReceiverId, userId).le(cursor != 0L, OrderMsg::getOrderMsgId, cursor).orderByDesc(OrderMsg::getOrderMsgId);
        }, OrderMsg::getOrderMsgId);
    }

    public OrderMsg latestMsg(Long userId) {
        Page<OrderMsg> page = this.lambdaQuery().eq(OrderMsg::getReceiverId, userId).orderByDesc(OrderMsg::getCreateTime)
                .page(new Page<>(1L, 1L));
        return (OrderMsg)page.getRecords().get(0);
    }
}