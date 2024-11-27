package pri.yqx.chat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import pri.yqx.chat.domain.entity.Room;

@Mapper
public interface RoomMapper extends BaseMapper<Room> {
}
