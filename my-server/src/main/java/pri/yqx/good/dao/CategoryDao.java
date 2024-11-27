package pri.yqx.good.dao;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import org.springframework.stereotype.Repository;
import pri.yqx.good.domain.entity.Category;
import pri.yqx.good.mapper.CategoryMapper;

@Repository
public class CategoryDao extends ServiceImpl<CategoryMapper, Category> {
    public CategoryDao() {
    }

    public List<Category> getCategories(List<String> categories) {
        return this.lambdaQuery().in(Category::getCategoryName, categories).list();
    }

    public void logicRemove(String categoryName) {
        this.lambdaUpdate().eq(Category::getCategoryName, categoryName).set(Category::getIsDeleted, true).update();
    }

    public List<Category> getByPkId(String pkId) {
        return this.lambdaQuery().eq(Category::getPkId, pkId).eq(Category::getIsDeleted, false).list();
    }
}