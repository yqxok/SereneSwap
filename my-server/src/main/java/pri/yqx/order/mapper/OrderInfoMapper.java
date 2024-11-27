package pri.yqx.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import pri.yqx.order.domain.entity.OrderInfo;

@Mapper
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {
}