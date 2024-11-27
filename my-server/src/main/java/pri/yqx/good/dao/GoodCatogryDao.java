//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.good.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;
import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.common.util.CursorUtil;
import pri.yqx.good.domain.entity.Category;
import pri.yqx.good.domain.entity.GoodCategory;
import pri.yqx.good.mapper.CategoryMapper;
import pri.yqx.good.mapper.GoodCategoryMapper;

@Repository
public class GoodCatogryDao extends ServiceImpl<GoodCategoryMapper, GoodCategory> {
    @Resource
    private CategoryMapper categoryMapper;

    public GoodCatogryDao() {
    }

    public CursorPageVo<GoodCategory> getCursorPage(Long cursor, Integer pageSize, String categoryName) {
        return CursorUtil.init(this, pageSize, (wrapper) -> {
           wrapper.eq(GoodCategory::getCategoryName, categoryName).le(cursor != 0L, GoodCategory::getGoodId, cursor)
                   .eq(GoodCategory::getIsDeleted, false).orderByDesc(GoodCategory::getGoodId);
        }, GoodCategory::getGoodId);
    }

    public List<String> getCategory(Long goodId) {
        List<GoodCategory> list = this.lambdaQuery().eq(GoodCategory::getGoodId, goodId).orderByAsc(GoodCategory::getLevel)
                .select(GoodCategory::getCategoryName).list();
        return list.stream().map(GoodCategory::getCategoryName).collect(Collectors.toList());
    }

    public void physicRemoveByGoodId(Long goodId) {
        this.remove(new LambdaQueryWrapper<GoodCategory>().eq(GoodCategory::getGoodId, goodId));
    }

    public void logicRemoveByGoodId(Long goodId) {
        this.lambdaUpdate().eq(GoodCategory::getGoodId, goodId).set(GoodCategory::getIsDeleted, true).update();
    }

    public List<Category> getCategoryByPkId(List<String> category) {
        return this.categoryMapper.getByPkId(category);
    }
}
