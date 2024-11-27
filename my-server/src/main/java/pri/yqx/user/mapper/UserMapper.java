package pri.yqx.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import pri.yqx.user.domain.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}