package pri.yqx.good.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import pri.yqx.good.domain.entity.Good;

@Mapper
public interface GoodMapper extends BaseMapper<Good> {
}