package pri.yqx.order.dao;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.*;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.common.util.CursorUtil;
import pri.yqx.common.util.MyBeanUtils;
import pri.yqx.order.domain.dto.OrderCursorReq;
import pri.yqx.order.domain.entity.Order;
import pri.yqx.order.domain.entity.OrderUnion;
import pri.yqx.order.mapper.OrderMapper;

@Component
public class OrderDao extends ServiceImpl<OrderMapper, Order> {
    @Resource
    private OrderMapper orderMapper;

    public OrderDao() {
    }

    public Map<Long, Order> getOrdersById(Set<Long> ids) {
        return CollectionUtil.isEmpty(ids)? new HashMap<>() : MyBeanUtils.transMap(new HashSet<>(this.listByIds(ids)), Order::getOrderId);
    }

    public CursorPageVo<OrderUnion> getCursorPage(Long userId, OrderCursorReq dto) {
        int pageSize = dto.getPageSize();
        dto.setPageSize(pageSize + 1);
        List<OrderUnion> list = this.orderMapper.getOrderPage(userId, dto);
        boolean isEnd = true;
        Long cursor = 0L;
        if (list.size() > pageSize) {
            isEnd = false;
            cursor = ((OrderUnion)CollectionUtil.getLast(list)).getOrderId();
            list.remove(list.size() - 1);
        }

        return CursorUtil.newCursorPageVo(cursor, isEnd, list);
    }

    public OrderUnion getOrderUnion(Long userId, Long orderId) {
        return this.orderMapper.getOrderUnion(userId, orderId);
    }
}