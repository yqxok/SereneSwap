package pri.yqx.user.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;
import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.common.util.CursorUtil;
import pri.yqx.user.domain.entity.Address;
import pri.yqx.user.mapper.AddressMapper;

@Repository
public class AddressDao extends ServiceImpl<AddressMapper, Address> {


    public CursorPageVo<Address> getCursorPage(Long cursor, Integer pageSize, Long userId) {
        return CursorUtil.init(this, pageSize, (lambda) -> {
            lambda.eq(Address::getUserId, userId).le(cursor != 0L, Address::getAddressId, cursor).orderByDesc(Address::getAddressId);
        }, Address::getAddressId);
    }

    public Address getDefaut(Long userId) {
        return this.lambdaQuery().eq(Address::getUserId, userId).eq(Address::getIsDefault, true).one();
    }

    public void setDefault(Long userId, Long addressId) {
       this.lambdaUpdate().eq(Address::getUserId, userId).set(Address::getIsDefault, false).update();
        this.lambdaUpdate().eq(Address::getUserId, userId).eq(Address::getAddressId, addressId).set(Address::getIsDefault, true).update();
    }

    public Address getAddress(Long userId, Long addressId) {
        return this.lambdaQuery().eq(Address::getUserId, userId).eq(Address::getAddressId, addressId).one();
    }

    public void removeAddress(Long userId, Long addressId) {
        this.remove(new LambdaQueryWrapper<Address>().eq(Address::getUserId, userId).eq(Address::getAddressId, addressId));
    }
}