package pri.yqx.comment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pri.yqx.comment.domain.dto.req.GoodJobReq;
import pri.yqx.comment.domain.entity.GoodJob;

public interface GoodJobService extends IService<GoodJob> {
    void saveGoodJob(Long userId, GoodJobReq goodJobDto);

    void deleteGoodJob(Long userId, GoodJobReq goodJobDto);
}