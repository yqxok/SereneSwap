package pri.yqx.msg.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import pri.yqx.msg.domain.entity.OrderMsg;

@Mapper
public interface OrderMsgMapper extends BaseMapper<OrderMsg> {
}