//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.good.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pri.yqx.common.util.AssertUtil;
import pri.yqx.good.dao.CategoryDao;
import pri.yqx.good.dao.GoodCatogryDao;
import pri.yqx.good.domain.dto.CategoryReq;
import pri.yqx.good.domain.entity.Category;
import pri.yqx.good.domain.vo.CategoryVo;
import pri.yqx.good.mapper.CategoryMapper;
import pri.yqx.good.service.CategoryService;

@Service
@Transactional
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Resource
    private CategoryDao categoryDao;
    @Resource
    private GoodCatogryDao goodCatogryDao;


    public void saveCategory(Long userId, CategoryReq categoryReq) {
        String categoryName = categoryReq.getCategoryName();
        AssertUtil.isTrue(categoryName.contains(" "), "分类标签不能带空格");
        Category category = (Category)this.categoryDao.getById(categoryName);
        AssertUtil.isEmpty(category, "该分类已存在");
        Category newCategory = (new Category()).setCategoryName(categoryReq.getCategoryName()).setCreateUser(userId);
        if (Objects.nonNull(categoryReq.getPkId())) {
            Category category1 = (Category)this.categoryDao.getById(categoryReq.getPkId());
            AssertUtil.isEmpty(category1, "pkId无效");
            newCategory.setPkId(category1.getCategoryName()).setLevel(category1.getLevel() + 1);
        }

        this.categoryDao.save(newCategory);
    }

    public void deleteById(String categoryName) {
        Category category = (Category)this.categoryDao.getById(categoryName);
        AssertUtil.isEmpty(category, "不存在该category");
        this.categoryDao.logicRemove(categoryName);
    }

    public CategoryVo getCategoryList(String pkId) {
        List<Category> list = this.categoryDao.getByPkId(pkId);
        if (CollectionUtil.isEmpty(list)) {
            return new CategoryVo();
        } else {
            List<String> collect = list.stream().map(Category::getCategoryName).collect(Collectors.toList());
            return new CategoryVo().setCategories(collect);
        }
    }

    public List<CategoryVo> getGoodCategores(Long goodId) {
        List<String> category = this.goodCatogryDao.getCategory(goodId);
        List<Category> categories = this.goodCatogryDao.getCategoryByPkId(category);
        return this.buildCategoryVos(category, categories);
    }

    private List<CategoryVo> buildCategoryVos(List<String> category, List<Category> categories) {
        List<List<Category>> arrayList = new ArrayList<>();
        ArrayList<Category> tmpList = new ArrayList<>();
        tmpList.add(categories.get(0));
        arrayList.add(tmpList);

        for(int i = 1; i < categories.size(); ++i) {
            List<Category> last = CollectionUtil.getLast(arrayList);
            if (!Objects.equals(last.get(0).getLevel(), categories.get(i).getLevel())) {
                last = new ArrayList<>();
                arrayList.add(last);
            }

           last.add(categories.get(i));
        }
        return arrayList.stream().map((ix) -> {
            List<String> list = ix.stream().map(Category::getCategoryName).collect(Collectors.toList());
            CategoryVo categoryVo = (new CategoryVo()).setCategories(list);
            for (String s : category) {
                int index = list.indexOf(s);
                if (index != -1) {
                    categoryVo.setChoose(index);
                    break;
                }
            }
            return categoryVo;
        }).collect(Collectors.toList());

    }
}
