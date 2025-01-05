package pri.yqx.ai.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Update;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pri.yqx.ai.domain.entity.AiRoom;
import pri.yqx.ai.domain.req.AiSummaryReq;
import pri.yqx.ai.domain.req.AigcReq;
import pri.yqx.ai.domain.vo.AiChatRoomVo;
import pri.yqx.ai.service.AiRoomService;
import pri.yqx.common.common.ThreadHolder;
import pri.yqx.common.domain.response.Result;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/aiRoom")
public class AiRoomController {
    @Autowired
    private AiRoomService aiRoomService;

    private final ChatClient chatClient;
    public AiRoomController(ChatClient.Builder chatClientBuilder){
        this.chatClient=chatClientBuilder.build();
    }
    /**
     * 创建与ai聊天新的会话
     * @return
     */
    @PostMapping
    public Result<Long> createNewRomm(){
        Long roomId= aiRoomService.createRoom(ThreadHolder.get());
        return Result.success(roomId,"创建成功");
    }
    /**
     * 获取当前用户所有会话
     * @return
     */
    @GetMapping
    public Result<List<AiChatRoomVo>> getRooms(){

        List<AiChatRoomVo> list=aiRoomService.getRooms(ThreadHolder.get());
        return Result.success(list,"查询成功");
    }
    /**
     * 总结会话内容
     * @return
     */
    @PostMapping("/summary")
    public Flux<String> summary(@RequestBody List<AigcReq.History> aiSummary) {
        ArrayList<Message> messages = new ArrayList<>();
        aiSummary.forEach(i-> {
            if(i.getType()==0)
                messages.add(new AssistantMessage(i.getContent()));
            else
                messages.add(new UserMessage(i.getContent()));
        });
        return chatClient.prompt(new Prompt(messages)).user("用不超过15字总结当前会话")
                .stream().content();
    }

    /**
     * 更新会话名称
     * @param roomId
     * @param roomName
     * @return
     */
    @PutMapping("/{roomId}")
    public Result<String> updateRoomTitle(@PathVariable Long roomId,@RequestParam String roomName){
        aiRoomService.updateRoomName(ThreadHolder.get(),roomId,roomName);
        return Result.success("修改成功");
    }

    /**
     * 删除会话
     * @param roomId
     * @return
     */
    @DeleteMapping("/{roomId}")
    public Result<String> deleteRoom(@PathVariable Long roomId){

        aiRoomService.deleteRoom(ThreadHolder.get(),roomId);
        return Result.success("删除成功");
    }
}
