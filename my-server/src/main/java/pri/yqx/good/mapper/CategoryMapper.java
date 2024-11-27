package pri.yqx.good.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import pri.yqx.good.domain.entity.Category;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
    List<Category> getByPkId(List<String> category);
}
