//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.good.controller;

import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pri.yqx.common.common.ThreadHolder;
import pri.yqx.common.domain.response.Result;
import pri.yqx.common.groups.Insert;
import pri.yqx.good.domain.dto.CategoryReq;
import pri.yqx.good.domain.vo.CategoryVo;
import pri.yqx.good.service.CategoryService;
import pri.yqx.good.service.GoodCategoryService;

@RestController
@RequestMapping({"/category"})
@Validated
public class CategoryController {
    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);
    @Resource
    private CategoryService categoryService;
    @Resource
    private GoodCategoryService goodCategoryService;

    public CategoryController() {
    }

    @PostMapping
    public Result<String> post(@RequestBody @Validated({Insert.class}) CategoryReq categoryReq) {
        this.categoryService.saveCategory(ThreadHolder.get(), categoryReq);
        return Result.success(null, "添加分类成功");
    }

    @DeleteMapping({"/{categoryName}"})
    public Result<String> deleteById(@PathVariable String categoryName) {
        this.categoryService.deleteById(categoryName);
        return Result.success(null, "删除成功");
    }

    @GetMapping
    public Result<CategoryVo> getById(@RequestParam(required = false,defaultValue = " ") String pkId) {
        CategoryVo categoryVo = this.categoryService.getCategoryList(pkId);
        return Result.success(categoryVo, "查询成功");
    }

    @GetMapping({"/{goodId}"})
    public Result<List<CategoryVo>> getAllCategories(@PathVariable Long goodId) {
        List<CategoryVo> categoryVos = this.categoryService.getGoodCategores(goodId);
        return Result.success(categoryVos, "商品分类查询成功");
    }
}
