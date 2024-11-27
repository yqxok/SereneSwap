package pri.yqx.good.service;

import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import pri.yqx.good.domain.dto.CategoryReq;
import pri.yqx.good.domain.entity.Category;
import pri.yqx.good.domain.vo.CategoryVo;

public interface CategoryService extends IService<Category> {
    void saveCategory(Long userId, CategoryReq category);

    void deleteById(String categoryName);

    CategoryVo getCategoryList(String pkId);

    List<CategoryVo> getGoodCategores(Long goodId);
}