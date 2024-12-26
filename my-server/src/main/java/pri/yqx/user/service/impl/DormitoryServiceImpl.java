package pri.yqx.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import java.util.List;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pri.yqx.user.dao.DormitoryDao;
import pri.yqx.user.domain.dto.DormiReq;
import pri.yqx.user.domain.entity.Dormitory;
import pri.yqx.user.service.DormitoryService;

@Service
@Transactional
public class DormitoryServiceImpl implements DormitoryService {
    @Autowired
    private DormitoryDao dormitoryDao;

    public DormitoryServiceImpl() {
    }

    public List<String> getDormitoryVos(String zone, String school) {
        List<Dormitory> list=null;
        if (school == null && zone == null) {
            list = this.dormitoryDao.lambdaQuery().groupBy(Dormitory::getSchool).select(Dormitory::getSchool).list();
            return list.stream().map(Dormitory::getSchool).collect(Collectors.toList());
        } else if (school != null && zone == null) {
            list = this.dormitoryDao.lambdaQuery().eq(Dormitory::getSchool, school).groupBy(Dormitory::getZone)
            .select(Dormitory::getZone).list();
            return list.stream().map(Dormitory::getZone).collect(Collectors.toList());
        } else if (school != null && zone != null) {
            list = this.dormitoryDao.lambdaQuery().eq(Dormitory::getSchool, school).eq(Dormitory::getZone, zone).list();
            return list.stream().map(Dormitory::getDormiName).collect(Collectors.toList());
        } else {
            throw new RuntimeException("dormitory Mapping 查询参数有误");
        }
    }

    public Long getDormiId(DormiReq dormiReq) {
        return this.dormitoryDao.lambdaQuery().eq(Dormitory::getSchool, dormiReq.getSchool()).eq(Dormitory::getZone, dormiReq.getZone())
                .eq(Dormitory::getDormiName, dormiReq.getDormiName()).one().getDormitoryId();
    }
}