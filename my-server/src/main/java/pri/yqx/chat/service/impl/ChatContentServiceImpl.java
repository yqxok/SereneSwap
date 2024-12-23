//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.chat.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pri.yqx.chat.dao.ChatContentDao;
import pri.yqx.chat.dao.ContactDao;

import pri.yqx.chat.domain.dto.req.ChatContentReq;
import pri.yqx.chat.domain.dto.req.ChatCursorReq;
import pri.yqx.chat.domain.entity.ChatContent;
import pri.yqx.chat.domain.entity.Contact;
import pri.yqx.chat.domain.vo.ChatContentVo;
import pri.yqx.chat.mapper.ChatContentMapper;
import pri.yqx.chat.service.ChatContentService;
import pri.yqx.chat.service.ContactService;
import pri.yqx.chat.service.adapter.ChatContentAdapter;
import pri.yqx.chat.service.event.ChatEvent;
import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.common.util.AssertUtil;
import pri.yqx.common.util.CursorUtil;
import pri.yqx.common.util.MyBeanUtils;
import pri.yqx.good.domain.entity.Good;
import pri.yqx.good.service.cache.GoodCache;
import pri.yqx.msg.domain.enums.MsgRoomType;
import pri.yqx.msg.service.MsgRoomService;
import pri.yqx.user.service.UserService;

@Service
@Transactional
public class ChatContentServiceImpl extends ServiceImpl<ChatContentMapper, ChatContent> implements ChatContentService {

    @Resource
    private ChatContentDao chatContentDao;

    @Resource
    private ContactService contactService;
    @Resource
    private ApplicationContext applicationContext;
    @Resource
    private GoodCache goodCache;
    @Resource
    private ContactDao contactDao;
    @Resource
    private MsgRoomService msgRoomService;



    public void updateContentRead(Long userId, Long goodId, Long otherId) {
        Contact contact = this.contactDao.getContact(userId, userId + goodId + otherId);
        AssertUtil.isEmpty(contact, "roomKey无效");
        this.msgRoomService.totalMsgRead(userId, contact.getNoReadNum());
        Contact contact1 = (new Contact()).setContactId(contact.getContactId()).setNoReadNum(0);
        this.contactDao.updateById(contact1);
    }

    public CursorPageVo<ChatContentVo> getCursorPage(Long userId, ChatCursorReq gDto) {
        Contact contact = this.contactDao.getContact(userId, userId + gDto.getGoodId() + gDto.getUserId());
        if (Objects.isNull(contact)) {
            return CursorUtil.newCursorPageVo(0L, false, null);
        } else {
            CursorPageVo<ChatContent> cursorPage = this.chatContentDao.getCursorPage(contact.getRoomKey(), gDto, contact.getEarlistTime());
            return ChatContentAdapter.buildChatVoCursorPage(cursorPage);
        }
    }

    @Transactional
    public Long sendMsg(Long userId, ChatContentReq cDto) {
        AssertUtil.isTrue(Objects.equals(userId, cDto.getReceiveUserId()), "不能和自己发消息");
        Good good = this.goodCache.getCache(cDto.getGoodId());
        AssertUtil.isEmpty(good, "goodId无效");
        long chatId = IdWorker.getId();
        long roomKey = cDto.getGoodId() + cDto.getReceiveUserId() + cDto.getSendUserId();
        this.chatContentDao.save((new ChatContent()).setReceiveUserId(cDto.getReceiveUserId()).setContent(cDto.getContent()).setChatId(chatId).setRoomKey(roomKey).setSendUserId(userId).setGoodId(cDto.getGoodId()));
        List<Contact> contacts = this.contactDao.getContactList(roomKey);
        if (CollectionUtil.isEmpty(contacts)) {
            this.contactService.createContact(cDto);
        } else {
            List<Contact> collect = contacts.stream().map((i) -> {
                Contact contact = (new Contact()).setIsDeleted(false).setContactId(i.getContactId()).setVersion(i.getVersion()).setLatestMsg(cDto.getContent());
                if (Objects.equals(i.getUserId(), cDto.getReceiveUserId())) {
                    contact.setNoReadNum(i.getNoReadNum() + 1);
                }
                return contact;
            }).collect(Collectors.toList());
            this.contactDao.updateBatchById(collect);
        }

        this.msgRoomService.noReadMsgAdd(cDto.getReceiveUserId(), 1, MsgRoomType.TOTAL_MSG_ROOM);
        this.applicationContext.publishEvent(new ChatEvent(this, chatId));
        return chatId;
    }

    public ChatContentVo getChatContent(Long id) {
        ChatContent chatContent = this.chatContentDao.getById(id);
        return MyBeanUtils.copyProperties(chatContent, new ChatContentVo());
    }
}
