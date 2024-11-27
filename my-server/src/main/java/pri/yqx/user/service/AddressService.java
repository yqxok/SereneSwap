package pri.yqx.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.user.domain.dto.AddressReq;
import pri.yqx.user.domain.entity.Address;
import pri.yqx.user.domain.vo.AddressVo;

public interface AddressService  {


    CursorPageVo<AddressVo> getAddressCursorPage(Long cursor, int pageSize, Long userId);

    void saveAddress(Long userId, AddressReq addressDto);

    void updateAddress(Long userId, AddressReq addressDto);

    void updateDefaultAddress(Long userId, Long addressId);

    AddressVo getDefaultAddress(Long userId);

    void removeAddress(Long userId, Long addressId);
}