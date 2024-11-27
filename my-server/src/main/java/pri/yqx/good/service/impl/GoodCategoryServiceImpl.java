package pri.yqx.good.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pri.yqx.good.domain.entity.GoodCategory;
import pri.yqx.good.mapper.GoodCategoryMapper;
import pri.yqx.good.service.GoodCategoryService;

@Service
public class GoodCategoryServiceImpl extends ServiceImpl<GoodCategoryMapper, GoodCategory> implements GoodCategoryService {
    public GoodCategoryServiceImpl() {
    }
}