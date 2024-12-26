//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.comment.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pri.yqx.comment.domain.dto.req.GoodJobReq;
import pri.yqx.comment.service.GoodJobService;
import pri.yqx.common.common.ThreadHolder;
import pri.yqx.common.domain.response.Result;

@RestController
@RequestMapping({"/goodJob"})
public class GoodJobController {
    @Autowired
    private GoodJobService goodJobService;

    public GoodJobController() {
    }

    @PostMapping
    public Result<String> saveGoodJob(@RequestBody GoodJobReq goodJobDto) {
        this.goodJobService.saveGoodJob(ThreadHolder.get(), goodJobDto);
        return Result.success(null, "点赞成功");
    }

    @DeleteMapping
    public Result<String> deleteGoodJob(@RequestBody GoodJobReq goodJobDto) {
        this.goodJobService.deleteGoodJob(ThreadHolder.get(), goodJobDto);
        return Result.success(null, "取消点赞成功");
    }
}
