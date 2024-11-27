package pri.yqx.user.service;

import java.util.List;
import pri.yqx.user.domain.dto.DormiReq;

public interface DormitoryService {
    List<String> getDormitoryVos(String zone, String school);

    Long getDormiId(DormiReq dormiReq);
}