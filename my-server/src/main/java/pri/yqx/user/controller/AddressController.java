//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.user.controller;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pri.yqx.common.common.ThreadHolder;
import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.common.domain.response.Result;
import pri.yqx.common.groups.Insert;
import pri.yqx.common.groups.Update;
import pri.yqx.user.domain.dto.AddressReq;
import pri.yqx.user.domain.dto.DormiReq;
import pri.yqx.user.domain.vo.AddressVo;
import pri.yqx.user.service.AddressService;
import pri.yqx.user.service.DormitoryService;

@RequestMapping({"/address"})
@RestController
public class AddressController {
    @Resource
    private AddressService addressService;
    @Resource
    private DormitoryService dormitoryService;



    @GetMapping({"/{cursor}/{pageSize}"})
    public Result<CursorPageVo<AddressVo>> addressList(@PathVariable Long cursor, @PathVariable int pageSize) {
        CursorPageVo<AddressVo> page = this.addressService.getAddressCursorPage(cursor, pageSize, ThreadHolder.get());
        return Result.success(page, "地址列表查询成功");
    }

    @GetMapping({"/default"})
    public Result<AddressVo> getDefaultAddress() {
        AddressVo defaultAddress = this.addressService.getDefaultAddress(ThreadHolder.get());
        return Result.success(defaultAddress, "默认地址查询成功");
    }

    @PostMapping
    public Result<String> saveAddress(@Validated({Insert.class}) @RequestBody AddressReq addressDto) {
        this.addressService.saveAddress(ThreadHolder.get(), addressDto);
        return Result.success(null, "地址保存成功");
    }

    @PutMapping
    public Result<String> updateAddress(@Validated({Update.class}) @RequestBody AddressReq addressDto) {
        this.addressService.updateAddress(ThreadHolder.get(), addressDto);
        return Result.success(null, "地址修改成功");
    }

    @PutMapping({"/default"})
    public Result<String> updateDefaultAddress(@Validated({Update.class}) @RequestBody AddressReq.DefaultAdrDto defaultAdrDto) {
        this.addressService.updateDefaultAddress(ThreadHolder.get(), defaultAdrDto.getAddressId());
        return Result.success(null, "默认地址更新成功");
    }

    @DeleteMapping({"/{addressId}"})
    public Result<String> deleteAddress(@PathVariable Long addressId) {
        this.addressService.removeAddress(ThreadHolder.get(), addressId);
        return Result.success(null, "地址删除成功");
    }

    @GetMapping({"/dormi"})
    public Result<List<String>> dormitoryVos(String zone, String school) {
        List<String> list = this.dormitoryService.getDormitoryVos(zone, school);
        return Result.success(list, "查询成功");
    }

    @PostMapping({"/id"})
    public Result<Long> getDormiId(@RequestBody DormiReq dormiReq) {
        Long dormiId = this.dormitoryService.getDormiId(dormiReq);
        return Result.success(dormiId, "dormiId获取成功");
    }
}
