package pri.yqx.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pri.yqx.user.domain.dto.LoginReq;
import pri.yqx.user.domain.dto.UserReq;
import pri.yqx.user.domain.entity.User;
import pri.yqx.user.domain.vo.UserVo;

public interface UserService extends IService<User> {
    String loginWithPhone(LoginReq loginDto);

    void signIn(LoginReq loginDto);



    void updateUser(Long userId, UserReq userDto);

    UserVo getUserVo(Long userId);

    UserVo getUserVo(String token);

    Integer getNoReadMessage(Long aLong);
}