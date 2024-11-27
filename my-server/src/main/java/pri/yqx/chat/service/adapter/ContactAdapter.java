package pri.yqx.chat.service.adapter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import pri.yqx.chat.domain.entity.Contact;
import pri.yqx.chat.domain.vo.ContactVo;
import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.common.util.CursorUtil;
import pri.yqx.good.domain.entity.Good;
import pri.yqx.good.domain.json.PicUrl;
import pri.yqx.user.domain.entity.User;

public class ContactAdapter {
    public ContactAdapter() {
    }

    public static CursorPageVo<ContactVo> buildContactVoCursorPage(CursorPageVo<Contact> cursorPage, Map<Long, User> userMap, Map<Long, Good> goodMap) {
        List<ContactVo> collect = cursorPage.getList().stream().map((i) -> {
            User user = userMap.get(i.getOtherId());
            Good good = goodMap.get(i.getGoodId());
            ContactVo contactVo = (new ContactVo()).setContactId(i.getContactId()).setNoReadNum(i.getNoReadNum()).setUpdateTime(i.getUpdateTime()).setLatestMsg(i.getLatestMsg());
            contactVo.setUserInfo((new ContactVo.UserInfo()).setUserId(user.getUserId()).setUserName(user.getUserName()).setProfilePicUrl(user.getProfilePicUrl()));
            contactVo.setGoodInfo((new ContactVo.GoodInfo()).setGoodId(good.getGoodId()).setPicUrl((PicUrl)good.getPicUrls().get(0)));
            return contactVo;
        }).collect(Collectors.toList());
        return CursorUtil.copyCursorPage(cursorPage, collect);
    }
}