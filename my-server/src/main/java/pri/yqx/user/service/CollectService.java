package pri.yqx.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import pri.yqx.common.domain.request.CursorReq;
import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.user.domain.entity.Collect;
import pri.yqx.user.domain.vo.CollectNumVo;
import pri.yqx.user.domain.vo.CollectVo;

public interface CollectService{
    Boolean saveCollect(Long userId, Long goodId);

    CursorPageVo<CollectVo> getCollectVoPage(Long userId, CursorReq cursorDto);

//    CollectNumVo getCollectNum(Long userId, Long goodId);


    Boolean deleteCollect(List<Long> goodIds, Long userId);

    Boolean isCollected(Long userId, Long goodId);
}