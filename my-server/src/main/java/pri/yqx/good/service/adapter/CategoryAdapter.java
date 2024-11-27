package pri.yqx.good.service.adapter;

import java.util.List;
import java.util.stream.Collectors;
import pri.yqx.good.domain.entity.Category;
import pri.yqx.good.domain.entity.GoodCategory;

public class CategoryAdapter {
    public CategoryAdapter() {
    }

    public static List<GoodCategory> buildGoodCategoryList(List<Category> categories, Long goodId) {
        return categories.stream().map(category ->
                new GoodCategory().setGoodId(goodId).setCategoryName(category.getCategoryName()).setLevel(category.getLevel())
        ).collect(Collectors.toList());
    }
}