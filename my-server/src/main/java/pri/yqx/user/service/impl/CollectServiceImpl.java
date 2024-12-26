//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pri.yqx.common.domain.request.CursorReq;
import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.common.exception.BusinessException;
import pri.yqx.common.util.AssertUtil;
import pri.yqx.common.util.MyBeanUtils;
import pri.yqx.good.dao.GoodDao;
import pri.yqx.good.domain.entity.Good;
import pri.yqx.good.service.GoodService;
import pri.yqx.good.service.cache.GoodCache;
import pri.yqx.user.dao.CollectDao;
import pri.yqx.user.domain.entity.Collect;
import pri.yqx.user.domain.entity.User;
import pri.yqx.user.domain.vo.CollectNumVo;
import pri.yqx.user.domain.vo.CollectVo;
import pri.yqx.user.mapper.CollectMapper;
import pri.yqx.user.service.CollectService;
import pri.yqx.user.service.UserService;
import pri.yqx.user.service.adapter.CollectAdapter;
import pri.yqx.user.service.cache.UserCache;

@Transactional
@Service
@Slf4j
public class CollectServiceImpl  implements CollectService {


    @Autowired
    private GoodService goodService;

    @Autowired
    private CollectDao collectDao;
    @Autowired
    private GoodCache goodCache;
    @Autowired
    private UserCache userCache;
    @Autowired
    private GoodDao goodDao;



    public Boolean saveCollect(Long userId, Long goodId) {
        Boolean collected = this.collectDao.isCollected(userId, goodId);
        AssertUtil.isTrue(collected, "该用户已经收藏此商品");
        this.collectDao.save((new Collect()).setGoodId(goodId).setUserId(userId));
        this.goodDao.plusCollectNum(goodId, userId);
        return true;
    }

    public CursorPageVo<CollectVo> getCollectVoPage(Long userId, CursorReq cursorDto) {
        CursorPageVo<Collect> page = this.collectDao.getCursorPage(cursorDto.getCursor(), cursorDto.getPageSize(), userId);
        Set<Long> goodIds = MyBeanUtils.getPropertySet(page.getList(), Collect::getGoodId);
        Set<Long> userIds = MyBeanUtils.getPropertySet(page.getList(), Collect::getUserId);
        Map<Long, Good> goodMap = this.goodCache.getAllCache(goodIds);
        Map<Long, User> userMap = this.userCache.getAllCache(userIds);
        return CollectAdapter.buildCollectVoCursorPage(page, goodMap, userMap);
    }

//    public CollectNumVo getCollectNum(Long userId, Long goodId) {
//        Good one = this.goodService.lambdaQuery().eq(Good::getGoodId, goodId).select(Good::getCollectNum).one();
//        Collect one1 = this.lambdaQuery().eq(Collect::getUserId, userId).eq(Collect::getGoodId, goodId).one();
//        CollectNumVo collectNumVo = (new CollectNumVo()).setGoodId(goodId).setUserId(userId).setCollectNum(one.getCollectNum());
//        collectNumVo.setIsCollected(one1 != null);
//        return collectNumVo;
//    }



    public Boolean deleteCollect(List<Long> goodIds, Long userId) {
        this.collectDao.deleteList(goodIds, userId);
        this.goodDao.substractCollectNum(goodIds, userId);
        return true;
    }

    public Boolean isCollected(Long userId, Long goodId) {
        return this.collectDao.isCollected(userId, goodId);
    }
}
