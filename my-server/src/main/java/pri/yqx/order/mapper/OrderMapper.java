package pri.yqx.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import pri.yqx.order.domain.dto.OrderCursorReq;
import pri.yqx.order.domain.entity.Order;
import pri.yqx.order.domain.entity.OrderUnion;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    List<OrderUnion> getOrderPage(Long userId, OrderCursorReq dto);

    OrderUnion getOrderUnion(Long userId, Long orderId);
}