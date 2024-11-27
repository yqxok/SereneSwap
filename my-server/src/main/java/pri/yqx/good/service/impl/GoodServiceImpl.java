//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.good.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.common.util.AssertUtil;
import pri.yqx.common.util.MyBeanUtils;
import pri.yqx.good.dao.CategoryDao;
import pri.yqx.good.dao.GoodCatogryDao;
import pri.yqx.good.dao.GoodDao;
import pri.yqx.good.domain.dto.GoodCursorReq;
import pri.yqx.good.domain.dto.GoodReq;
import pri.yqx.good.domain.entity.Category;
import pri.yqx.good.domain.entity.Good;
import pri.yqx.good.domain.entity.GoodCategory;
import pri.yqx.good.domain.vo.GoodDetailVo;
import pri.yqx.good.domain.vo.GoodVo;
import pri.yqx.good.mapper.GoodMapper;
import pri.yqx.good.service.GoodService;
import pri.yqx.good.service.adapter.CategoryAdapter;
import pri.yqx.good.service.adapter.GoodAdapter;
import pri.yqx.good.service.cache.GoodCache;
import pri.yqx.user.domain.entity.User;
import pri.yqx.user.service.cache.UserCache;

@Service
@Transactional
@Lazy
public class GoodServiceImpl extends ServiceImpl<GoodMapper, Good> implements GoodService {
    private static final Logger log = LoggerFactory.getLogger(GoodServiceImpl.class);
    @Resource
    private GoodDao goodDao;
    @Resource
    private GoodCatogryDao goodCatogryDao;
    @Resource
    private GoodCache goodCache;
    @Resource
    private UserCache userCache;
    @Resource
    private CategoryDao categoryDao;

    public GoodServiceImpl() {
    }

    public Long saveGood(Long userId, GoodReq goodDto) {
        List<Category> categories = this.categoryDao.getCategories(goodDto.getCategories());
        AssertUtil.ltSize(goodDto.getCategories(), categories.size(), "分类标签有误");
        Long goodId = IdWorker.getId();
        this.goodDao.save(((Good)MyBeanUtils.copyProperties(goodDto, new Good())).setGoodId(goodId).setUserId(userId));
        this.goodCatogryDao.saveBatch(CategoryAdapter.buildGoodCategoryList(categories, goodId));
        return goodId;
    }

    public CursorPageVo<GoodVo> pageGoodVo(GoodCursorReq goodCursorReq) {
        CursorPageVo<Good> cursorPage = null;
        if (Objects.isNull(goodCursorReq.getCategoryName())) {
            cursorPage = this.goodDao.getCursorPage(goodCursorReq.getCursor(), goodCursorReq.getPageSize());
        } else {
            CursorPageVo<GoodCategory> cursorPage1 = this.goodCatogryDao.getCursorPage(goodCursorReq.getCursor(), goodCursorReq.getPageSize(), goodCursorReq.getCategoryName());
            cursorPage = GoodAdapter.buildGoodCursorPage(cursorPage1, this.goodCache);
        }

        Set<Long> userIds = MyBeanUtils.getPropertySet(cursorPage.getList(), Good::getUserId);
        Map<Long, User> users = this.userCache.getAllCache(userIds);
        return GoodAdapter.buildGoodVoCursorPage(cursorPage, users);
    }

    public List<GoodVo> listGoodVoById(Long userId, Short status) {
        User user = this.userCache.getCache(userId);
        AssertUtil.isEmpty(user, "userId无效");
        List<Good> goods = this.goodDao.getGoodsByUserId(userId, status);
        return GoodAdapter.buildGoodVoList(goods, user);
    }

    public void deleteGoodById(Long goodId, Long userId) {
        Long count = this.goodDao.isGoodExit(goodId, userId);
        AssertUtil.isZero(count, "该商品id无效");
        this.goodCatogryDao.logicRemoveByGoodId(goodId);
        this.goodDao.logicRemove(goodId);
    }

    public GoodDetailVo getGoodDetailVo(Long goodId) {
        Good good = this.goodCache.getPhysicExitCache(goodId);
        AssertUtil.isEmpty(good, "goodId无效");
        User userInfo = this.userCache.getCache(good.getUserId());
        List<String> category = this.goodCatogryDao.getCategory(goodId);
        return GoodAdapter.bildGoodDetailVo(good, userInfo, category);
    }

    public Long updateGood(Long userId, GoodReq goodDto) {
        Long num = this.goodDao.isGoodExit(goodDto.getGoodId(), userId);
        AssertUtil.isZero(num, "该goodId无效");
        if (CollectionUtil.isNotEmpty(goodDto.getCategories())) {
            List<Category> categories = this.categoryDao.getCategories(goodDto.getCategories());
            AssertUtil.ltSize(goodDto.getCategories(), categories.size(), "分类标签有误");
            this.goodCatogryDao.physicRemoveByGoodId(goodDto.getGoodId());
            this.goodCatogryDao.saveBatch(CategoryAdapter.buildGoodCategoryList(categories, goodDto.getGoodId()));
        }

        this.goodDao.updateById(MyBeanUtils.copyProperties(goodDto, new Good()));
        return goodDto.getGoodId();
    }

    public void updateGoodById(Good good) {
        this.goodDao.updateById(good);
        this.goodCache.rmCache(good.getGoodId());
    }
}
