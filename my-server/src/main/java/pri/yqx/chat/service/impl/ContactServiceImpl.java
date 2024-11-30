//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.chat.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pri.yqx.chat.dao.ChatContentDao;
import pri.yqx.chat.dao.ContactDao;
//import pri.yqx.chat.dao.RoomDao;
import pri.yqx.chat.domain.dto.req.ChatContentReq;
import pri.yqx.chat.domain.entity.Contact;
import pri.yqx.chat.domain.vo.ContactVo;
import pri.yqx.chat.service.ContactService;
import pri.yqx.chat.service.adapter.ContactAdapter;
import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.common.util.AssertUtil;
import pri.yqx.common.util.MyBeanUtils;
import pri.yqx.good.domain.entity.Good;
import pri.yqx.good.service.cache.GoodCache;
import pri.yqx.user.domain.entity.User;
import pri.yqx.user.service.cache.UserCache;

@Service
public class ContactServiceImpl implements ContactService {
    private static final String DEFAULT_READ_TIME = "2000-01-01 00:00:00";
    @Resource
    private ContactDao contactDao;
//    @Resource
//    private RoomDao roomDao;
    @Resource
    private UserCache userCache;
    @Resource
    private GoodCache goodCache;
    @Resource
    private ChatContentDao chatContentDao;



    @Transactional
    @Override
    public void createContact(ChatContentReq cReq) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime earlistTime = LocalDateTime.parse("2000-01-01 00:00:00", formatter);
        Long roomKey = cReq.getGoodId() + cReq.getSendUserId() + cReq.getReceiveUserId();
        Contact contact = (new Contact()).setGoodId(cReq.getGoodId()).setLatestMsg(cReq.getContent()).setNoReadNum(1).setUserId(cReq.getReceiveUserId()).setOtherId(cReq.getSendUserId()).setEarlistTime(earlistTime).setRoomKey(roomKey);
        Contact contact1 = (new Contact()).setGoodId(cReq.getGoodId()).setLatestMsg(cReq.getContent()).setUserId(cReq.getSendUserId()).setOtherId(cReq.getReceiveUserId()).setEarlistTime(earlistTime).setRoomKey(roomKey);
        List<Contact> contacts = Arrays.asList(contact, contact1);
        this.contactDao.saveBatch(contacts);
    }

    @Transactional
    @Override
    public CursorPageVo<ContactVo> getCursorContact(Long userId, Long cursor, Integer pageSize) {
        CursorPageVo<Contact> cursorPage = this.contactDao.getCursorPage(userId, cursor, pageSize);
        Map<Long, User> userMap = this.userCache.getAllCache(MyBeanUtils.getPropertySet(cursorPage.getList(), Contact::getOtherId));
        Map<Long, Good> goodMap = this.goodCache.getAllCache(MyBeanUtils.getPropertySet(cursorPage.getList(), Contact::getGoodId));
        return ContactAdapter.buildContactVoCursorPage(cursorPage, userMap, goodMap);
    }
    @Override
    public void deleteContact(Long userId, Long contactId) {
        Contact contact = this.contactDao.getContactById(userId, contactId);
        AssertUtil.isEmpty(contact, "contactId无效");
        LocalDateTime latestMsgTime = this.chatContentDao.getLatestMsgTime(contact.getGoodId() + contact.getUserId() + contact.getOtherId());
        Contact contact1 = new Contact().setContactId(contact.getContactId()).setIsDeleted(true).setEarlistTime(latestMsgTime);
        this.contactDao.updateById(contact1);
    }
}
