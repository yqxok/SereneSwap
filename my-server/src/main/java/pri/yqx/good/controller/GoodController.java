//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.good.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pri.yqx.common.common.ThreadHolder;
import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.common.domain.response.Result;
import pri.yqx.common.groups.Insert;
import pri.yqx.common.groups.Update;
import pri.yqx.good.domain.dto.GoodCursorReq;
import pri.yqx.good.domain.dto.GoodReq;
import pri.yqx.good.domain.dto.SelfGoodReq;
import pri.yqx.good.domain.vo.GoodCursorPageVo;
import pri.yqx.good.domain.vo.GoodDetailVo;
import pri.yqx.good.domain.vo.GoodVo;
import pri.yqx.good.service.GoodService;

@RestController
@RequestMapping({"/good"})
public class GoodController {
    private static final Logger log = LoggerFactory.getLogger(GoodController.class);
    @Resource
    private GoodService goodService;

    @PostMapping
    public Result<Long> postGood(@RequestBody @Validated({Insert.class}) GoodReq goodDto) {
        log.info("goodDto={}", goodDto);
        Long goodId = this.goodService.saveGood(ThreadHolder.get(), goodDto);
        return Result.success(goodId, "商品保存成功");
    }

    /**
     * 未登录状态获取商品分页请求
     * @param goodCursorReq
     * @return
     */
    @PostMapping({"/no/page"})
    public Result<GoodCursorPageVo> getGoodPage(@Valid @RequestBody GoodCursorReq goodCursorReq) {
        GoodCursorPageVo goodVoCursorPageVo = this.goodService.pageGoodVo(goodCursorReq);
        return Result.success(goodVoCursorPageVo, "查询成功");
    }

    @GetMapping({"/no/{goodId}"})
    public Result<GoodDetailVo> getGoodDetailVo(@PathVariable Long goodId) {
        GoodDetailVo goodDetailVo = this.goodService.getGoodDetailVo(goodId);
        return Result.success(goodDetailVo, "商品信息查询成功");
    }

    @PostMapping({"/no/list/{userId}"})
    public Result<GoodCursorPageVo> getGoodListById(@PathVariable Long userId,@RequestBody SelfGoodReq selfGoodReq) {
        GoodCursorPageVo goodVos = this.goodService.listGoodVoById(userId, selfGoodReq);
        return Result.success(goodVos, "用户商品查询成功");
    }

    @DeleteMapping({"/{goodId}"})
    public Result<String> deleteGoodById(@PathVariable Long goodId) {
        this.goodService.deleteGoodById(goodId, ThreadHolder.get());
        return Result.success("商品删除成功");
    }

    @PutMapping
    public Result<Long> updateGood(@RequestBody(required = true) @Validated({Update.class}) GoodReq goodDto) {
        Long aLong = this.goodService.updateGood(ThreadHolder.get(), goodDto);
        return Result.success(aLong, "商品更新成功");
    }
}
