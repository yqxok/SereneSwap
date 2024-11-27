package pri.yqx.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import pri.yqx.user.domain.entity.Address;

@Mapper
public interface AddressMapper extends BaseMapper<Address> {
}