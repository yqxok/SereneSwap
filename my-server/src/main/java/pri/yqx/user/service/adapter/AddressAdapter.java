package pri.yqx.user.service.adapter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.common.util.CursorUtil;
import pri.yqx.common.util.MyBeanUtils;
import pri.yqx.user.domain.entity.Address;
import pri.yqx.user.domain.entity.Dormitory;
import pri.yqx.user.domain.vo.AddressVo;

public class AddressAdapter {


    public static CursorPageVo<AddressVo> buildAddressVoCursorPage(CursorPageVo<Address> page, Map<Long, Dormitory> dormiMap) {
        List<AddressVo> collect = page.getList().stream().map((i) -> {
            AddressVo addressVo = MyBeanUtils.copyProperties(i, new AddressVo());
            return addressVo.setDormiInfo(MyBeanUtils.copyProperties(dormiMap.get(i.getDormitoryId()), new AddressVo.DormitoryInfo()));
        }).collect(Collectors.toList());
        return CursorUtil.newCursorPageVo(page.getCursor(), page.getIsEnd(), collect);
    }

    public static AddressVo buildAddressVo(Address address, Dormitory dormitory) {
        AddressVo addressVo = MyBeanUtils.copyProperties(address, new AddressVo());
        return addressVo.setDormiInfo(MyBeanUtils.copyProperties(dormitory, new AddressVo.DormitoryInfo()));
    }
}
