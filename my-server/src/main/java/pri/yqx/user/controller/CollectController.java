//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.user.controller;

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
import org.springframework.web.bind.annotation.RestController;
import pri.yqx.common.common.ThreadHolder;
import pri.yqx.common.domain.request.CursorReq;
import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.common.domain.response.Result;
import pri.yqx.common.groups.Insert;
import pri.yqx.user.domain.dto.CollectReq;
import pri.yqx.user.domain.vo.CollectVo;
import pri.yqx.user.service.CollectService;

@RestController
@RequestMapping({"/collect"})
public class CollectController {
    private static final Logger log = LoggerFactory.getLogger(CollectController.class);
    @Resource
    private CollectService collectService;

    public CollectController() {
    }

    @PostMapping({"/page"})
    public Result<CursorPageVo<CollectVo>> page(@Validated @RequestBody CursorReq cursorDto) {
        CursorPageVo<CollectVo> page = this.collectService.getCollectVoPage(ThreadHolder.get(), cursorDto);
        return Result.success(page, "收藏查询成功");
    }

    @GetMapping({"/{goodId}"})
    public Result<Boolean> isCollected(@PathVariable Long goodId) {
        Boolean check = this.collectService.isCollected(ThreadHolder.get(), goodId);
        return Result.success(check, "收藏获取成功");
    }

    @PostMapping
    public Result<Boolean> saveCollect(@Validated({Insert.class}) @RequestBody CollectReq collectDto) {
        Boolean res = this.collectService.saveCollect(ThreadHolder.get(), collectDto.getGoodId());
        return Result.success(res, "收藏保存成功");
    }

    @DeleteMapping
    public Result<Boolean> deleteCollect(@RequestBody List<Long> goodIds) {
        this.collectService.deleteCollect(goodIds, ThreadHolder.get());
        return Result.success(true, "收藏取消成功");
    }
}
