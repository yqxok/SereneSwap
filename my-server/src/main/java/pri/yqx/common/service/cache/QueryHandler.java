package pri.yqx.common.service.cache;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.util.Collection;

public interface QueryHandler<T> {
    void handle(LambdaQueryWrapper<T> wrapper, Collection<Long> ids);
}
