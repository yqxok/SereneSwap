//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pri.yqx.common.exception.BusinessException;
import pri.yqx.common.util.AssertUtil;
import pri.yqx.common.util.JwtUtil;
import pri.yqx.common.util.MyBeanUtils;
import pri.yqx.msg.service.MsgRoomService;
import pri.yqx.user.dao.UserDao;
import pri.yqx.user.domain.dto.LoginReq;
import pri.yqx.user.domain.dto.UserReq;
import pri.yqx.user.domain.entity.User;
import pri.yqx.user.domain.vo.UserVo;
import pri.yqx.user.mapper.UserMapper;
import pri.yqx.user.service.UserService;
import pri.yqx.user.service.adapter.UserAdapter;
import pri.yqx.user.service.cache.UserCache;

@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserCache userCache;
    @Autowired
    private MsgRoomService msgRoomService;



    public String loginWithPhone(LoginReq loginDto) {
        User user = this.userDao.getByPhone(loginDto.getPhoneNumber(), loginDto.getPassword());
        AssertUtil.isEmpty(user, "账号或密码错误");
        return JwtUtil.getToken(user.getUserId());
    }

    public void signIn(LoginReq loginDto) {
        User user = this.userDao.getByPhone(loginDto.getPhoneNumber(), loginDto.getPassword());
        AssertUtil.isNotEmpty(user, "账号已存在");
        long userId = IdWorker.getId();
        this.userDao.save(UserAdapter.buildNewUser(loginDto, userId));
        this.msgRoomService.createMsgRoom(userId);
    }



    public void updateUser(Long userId, UserReq userDto) {
        this.userDao.updateById((MyBeanUtils.copyProperties(userDto, new User())).setUserId(userId));
        this.userCache.rmCache(userId);
    }

    public UserVo getUserVo(Long userId) {
        return MyBeanUtils.copyProperties(this.userCache.getCache(userId), new UserVo());
    }

    public UserVo getUserVo(String token) {
        User user = this.userDao.getById(JwtUtil.verifyAndGetId(token));
        return MyBeanUtils.copyProperties(user, new UserVo());
    }

    public Integer getNoReadMessage(Long userId) {
        return this.userDao.getNoReadMessage(userId);
    }
}
