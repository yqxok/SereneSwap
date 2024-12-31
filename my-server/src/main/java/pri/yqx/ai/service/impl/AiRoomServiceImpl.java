package pri.yqx.ai.service.impl;


import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pri.yqx.ai.dao.AiRoomDao;
import pri.yqx.ai.domain.entity.AiRoom;
import pri.yqx.ai.domain.vo.AiChatRoomVo;
import pri.yqx.ai.mapper.AiRoomMapper;
import pri.yqx.ai.service.AiRoomService;
import pri.yqx.common.common.ThreadHolder;
import pri.yqx.common.constant.RedisKey;
import pri.yqx.common.util.MyBeanUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AiRoomServiceImpl extends AiRoomDao implements AiRoomService {


    @Override
    public Long createRoom(Long userId) {
        long id = IdWorker.getId();
        AiRoom aiRoom = new AiRoom().setRoomId(id).setUserId(userId);
        this.save(aiRoom);
        return id;
    }

    @Override
    public List<AiChatRoomVo> getRooms(Long userId) {
        List<AiRoom> list = this.lambdaQuery().eq(AiRoom::getUserId, userId).list();
        return list.stream().map(i->MyBeanUtils.copyProperties(i,new AiChatRoomVo())).collect(Collectors.toList());
    }


}