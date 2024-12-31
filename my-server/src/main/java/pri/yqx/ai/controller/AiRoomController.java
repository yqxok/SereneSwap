package pri.yqx.ai.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Update;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pri.yqx.ai.domain.entity.AiRoom;
import pri.yqx.ai.domain.req.AiSummaryReq;
import pri.yqx.ai.domain.vo.AiChatRoomVo;
import pri.yqx.ai.service.AiRoomService;
import pri.yqx.common.common.ThreadHolder;
import pri.yqx.common.domain.response.Result;
import reactor.core.publisher.Flux;

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
     * 获取当前用户所有会话
     * @return
     */
    @PutMapping
    public Flux<String> summary(@RequestBody AiSummaryReq aiSummary) {
        return chatClient.prompt().user(aiSummary.getContent()).stream().content();
    }
}
