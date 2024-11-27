package pri.yqx.msg.dao;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import pri.yqx.msg.domain.entity.MsgRoom;
import pri.yqx.msg.domain.enums.MsgRoomType;
import pri.yqx.msg.mapper.MsgRoomMapper;

@Component
public class MsgRoomDao extends ServiceImpl<MsgRoomMapper, MsgRoom> {
    public MsgRoomDao() {
    }

    public Integer getNoReadNum(Long userId, MsgRoomType type) {
        return this.lambdaQuery().eq(MsgRoom::getUserId, userId).eq(MsgRoom::getType, type.getType()).one().getNoReadNum();
    }

    public void updateNoReadNum(Long userId, Integer type, int i) {
        this.lambdaUpdate().eq(MsgRoom::getType, type).eq(MsgRoom::getUserId, userId).set(MsgRoom::getNoReadNum, i).update();
    }

    public MsgRoom getMsgRoom(Long receiverId, MsgRoomType type) {
        return this.lambdaQuery().eq(MsgRoom::getUserId, receiverId).eq(MsgRoom::getType, type.getType()).one();
    }

    public List<MsgRoom> getMsgRooms(Long userId, MsgRoomType... type) {
        List<Integer> types = Arrays.stream(type).map(MsgRoomType::getType).collect(Collectors.toList());
        return this.lambdaQuery().eq(MsgRoom::getUserId, userId).in(MsgRoom::getType, types).list();
    }
}