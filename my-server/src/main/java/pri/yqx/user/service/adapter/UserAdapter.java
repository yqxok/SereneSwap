package pri.yqx.user.service.adapter;

import java.util.Random;
import pri.yqx.user.domain.dto.LoginReq;
import pri.yqx.user.domain.entity.User;

public class UserAdapter {


    public static User buildNewUser(LoginReq loginDto, Long userId) {
        return (new User()).setUserId(userId).setUserName("莞工er" + (new Random()).nextInt(10000)).setProfilePicUrl("https://tse1-mm.cn.bing.net/th/id/OIP-C.QDl_Z7HdQWX_XbVYgBLJLQAAAA?rs=1&pid=ImgDetMain").setPassword(loginDto.getPassword()).setPhoneNumber(loginDto.getPhoneNumber());
    }
}