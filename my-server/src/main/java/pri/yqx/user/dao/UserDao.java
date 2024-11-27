package pri.yqx.user.dao;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;
import pri.yqx.user.domain.entity.User;
import pri.yqx.user.mapper.UserMapper;

@Repository
public class UserDao extends ServiceImpl<UserMapper, User> {
    public UserDao() {
    }

    public User getByPhone(String phone, String password) {
        return this.lambdaQuery().eq(User::getPhoneNumber, phone).eq(User::getPassword, password).one();
    }

    public Integer getNoReadMessage(Long userId) {
        User one = this.lambdaQuery().eq(User::getUserId, userId).select(User::getNoReadNum).one();
        return one.getNoReadNum();
    }
}
