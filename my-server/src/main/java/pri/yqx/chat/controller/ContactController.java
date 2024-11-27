package pri.yqx.chat.controller;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pri.yqx.chat.domain.vo.ContactVo;
import pri.yqx.chat.service.ContactService;
import pri.yqx.common.common.ThreadHolder;
import pri.yqx.common.domain.response.CursorPageVo;
import pri.yqx.common.domain.response.Result;

@RestController
@RequestMapping({"/contact"})
public class ContactController {
    @Resource
    private ContactService contactService;



    @GetMapping({"/{cursor}/{pageSize}"})
    public Result<CursorPageVo<ContactVo>> getChatRooms(@PathVariable Long cursor, @PathVariable Integer pageSize) {
        CursorPageVo<ContactVo> cursorPage = this.contactService.getCursorContact(ThreadHolder.get(), cursor, pageSize);
        return Result.success(cursorPage, "会话列表查询成功");
    }

    @DeleteMapping({"/{contactId}"})
    public Result<String> removeContact(@PathVariable Long contactId) {
        this.contactService.deleteContact(ThreadHolder.get(), contactId);
        return Result.success(null, "会话删除完毕");
    }
}