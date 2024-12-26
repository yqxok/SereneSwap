package pri.yqx.chat.dao;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pri.yqx.chat.domain.entity.Contact;
import pri.yqx.chat.mapper.ContactMapper;
import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.common.util.CursorUtil;

@Component
public class ContactDao extends ServiceImpl<ContactMapper, Contact> {
    @Autowired
    private ContactMapper contactMapper;


    public CursorPageVo<Contact> getCursorPage(Long userId, Long cursor, Integer pageSize) {
        return CursorUtil.init(this, pageSize, (lambda) -> {
           lambda.le(cursor != 0L, Contact::getUpTimeStamp, cursor)
                   .eq(Contact::getUserId, userId).eq(Contact::getIsDeleted, false).orderByDesc(Contact::getUpTimeStamp);
        }, Contact::getUpTimeStamp);
    }

    public Contact getContact(Long userId, Long roomKey) {
        return this.lambdaQuery().eq(Contact::getUserId, userId).eq(Contact::getRoomKey, roomKey).one();
    }

    public Contact getContactById(Long userId, Long contactId) {
        return this.lambdaQuery().eq(Contact::getUserId, userId).eq(Contact::getContactId, contactId).one();
    }

    public List<Contact> getContactList(long roomKey) {
        return this.lambdaQuery().eq(Contact::getRoomKey, roomKey).list();
    }
}