package pri.yqx.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;
import pri.yqx.user.domain.entity.Dormitory;
import pri.yqx.user.mapper.DormitoryMapper;

@Component
public class DormitoryDao extends ServiceImpl<DormitoryMapper, Dormitory> {

}