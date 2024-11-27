//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.user.controller;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pri.yqx.common.common.ThreadHolder;
import pri.yqx.common.domain.response.Result;
import pri.yqx.common.util.JwtUtil;
import pri.yqx.user.domain.dto.LoginReq;
import pri.yqx.user.domain.dto.UserReq;
import pri.yqx.user.domain.vo.UserVo;
import pri.yqx.user.service.UserService;

@RestController
@RequestMapping({"/user"})
@Validated
@Slf4j
public class UserController {

    @Resource
    private UserService userService;



    @PostMapping({"/no/lgByP"})
    public Result<String> loginWithPhone(@RequestBody LoginReq loginDto) {
        String token = this.userService.loginWithPhone(loginDto);
        return Result.success(token, "登录成功");
    }

    @PostMapping({"/no/signIn"})
    public Result<String> signIn(@RequestBody LoginReq loginDto) {
        this.userService.signIn(loginDto);
        return Result.success(null, "账号注册成功");
    }

    @PutMapping
    public Result<String> updateUser(@Validated @RequestBody UserReq userDto) {
        this.userService.updateUser(ThreadHolder.get(), userDto);
        return Result.success(null, "用户更新成功");
    }

    @GetMapping({"/no/{userId}"})
    public Result<UserVo> getUser(@PathVariable Long userId) {
        UserVo userVo = this.userService.getUserVo(userId);
        return Result.success(userVo, "用户查询成功");
    }

    @GetMapping({"/no/token"})
    public Result<UserVo> getUserByToken(@RequestParam(required = true) String token) {
        UserVo userVo = this.userService.getUserVo(token);
        return Result.success(userVo, "用户查询成功");
    }

    @GetMapping({"/no/udToken"})
    public Result<String> updateToken(@RequestParam(required = true) String token) {
        String token1 = null;

        try {
            token1 = JwtUtil.updateToken(token);
        } catch (Exception var4) {
            return Result.success(null, var4.getMessage());
        }

        return Result.success(token1, "token更新成功");
    }

    @GetMapping({"/noRead"})
    public Result<Integer> getNoReadMessage() {
        Integer noReadMessage = this.userService.getNoReadMessage(ThreadHolder.get());
        return Result.success(noReadMessage, "未读消息数查询成功");
    }
}
