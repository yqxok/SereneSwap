package pri.yqx.good.service;

import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.good.domain.dto.GoodCursorReq;
import pri.yqx.good.domain.dto.GoodReq;
import pri.yqx.good.domain.entity.Good;
import pri.yqx.good.domain.vo.GoodDetailVo;
import pri.yqx.good.domain.vo.GoodVo;

public interface GoodService extends IService<Good> {
    Long saveGood(Long userId, GoodReq goodDto);

    CursorPageVo<GoodVo> pageGoodVo(GoodCursorReq goodCursorReq);

    List<GoodVo> listGoodVoById(Long userId, Short status);

    void deleteGoodById(Long goodId, Long userId);

    GoodDetailVo getGoodDetailVo(Long goodId);

    Long updateGood(Long userId, GoodReq goodDto);

    void updateGoodById(Good good);
}