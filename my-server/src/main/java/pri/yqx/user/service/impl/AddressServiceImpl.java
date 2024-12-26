//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.user.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.common.exception.BusinessException;
import pri.yqx.common.util.AssertUtil;
import pri.yqx.common.util.MyBeanUtils;
import pri.yqx.user.dao.AddressDao;
import pri.yqx.user.dao.DormitoryDao;
import pri.yqx.user.domain.dto.AddressReq;
import pri.yqx.user.domain.entity.Address;
import pri.yqx.user.domain.entity.Dormitory;
import pri.yqx.user.domain.vo.AddressVo;
import pri.yqx.user.mapper.AddressMapper;
import pri.yqx.user.service.AddressService;
import pri.yqx.user.service.DormitoryService;
import pri.yqx.user.service.UserService;
import pri.yqx.user.service.adapter.AddressAdapter;

@Service
@Transactional
public class AddressServiceImpl  implements AddressService {
    private static final Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private DormitoryService dormitoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private AddressDao addressDao;
    @Autowired
    private DormitoryDao dormitoryDao;

    @Override
    public CursorPageVo<AddressVo> getAddressCursorPage(Long cursor, int pageSize, Long userId) {
        CursorPageVo<Address> page = this.addressDao.getCursorPage(cursor, pageSize, userId);
        Set<Long> dormiIds = MyBeanUtils.getPropertySet(page.getList(), Address::getDormitoryId);
        List<Dormitory> dormitories = this.dormitoryDao.listByIds(dormiIds);
        Map<Long, Dormitory> dormiMap = MyBeanUtils.transMap(dormitories.stream().collect(Collectors.toSet()), Dormitory::getDormitoryId);
        return AddressAdapter.buildAddressVoCursorPage(page, dormiMap);
    }
    @Override
    public void saveAddress(Long userId, AddressReq addressDto) {
        Dormitory dormitory = (Dormitory)this.dormitoryDao.getById(addressDto.getDormitoryId());
        AssertUtil.isEmpty(dormitory, "dormitoryId无效");
        this.addressDao.save(MyBeanUtils.copyProperties(addressDto, (new Address()).setUserId(userId)));
    }
    @Override
    public void updateAddress(Long userId, AddressReq addressDto) {
        AssertUtil.isEmpty(this.addressDao.getAddress(userId, addressDto.getAddressId()), "addressId无效");
        if (!Objects.isNull(addressDto.getDormitoryId())) {
            AssertUtil.isEmpty(this.dormitoryDao.getById(addressDto.getDormitoryId()), "dormitoryId无效");
        }

        this.addressDao.updateById(MyBeanUtils.copyProperties(addressDto, new Address()));
    }
    @Override
    public void updateDefaultAddress(Long userId, Long addressId) {
        AssertUtil.isEmpty(this.addressDao.getAddress(userId, addressId), "该addressId无效");
        this.addressDao.setDefault(userId, addressId);
    }
    @Override
    public AddressVo getDefaultAddress(Long userId) {
        Address address = this.addressDao.getDefaut(userId);
        AssertUtil.isEmpty(address, "不存在默认地址");
        Dormitory dormitory = (Dormitory)this.dormitoryDao.getById(address.getDormitoryId());
        return AddressAdapter.buildAddressVo(address, dormitory);
    }
    @Override
    public void removeAddress(Long userId, Long addressId) {
        AssertUtil.isEmpty(this.addressDao.getAddress(userId, addressId), "该addressId无效");
        this.addressDao.removeAddress(userId, addressId);
    }
}
