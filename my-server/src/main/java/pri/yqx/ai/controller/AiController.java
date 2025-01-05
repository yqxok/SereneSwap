package pri.yqx.ai.controller;
//
import cn.hutool.core.io.resource.ResourceUtil;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.reader.JsonReader;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.FileUrlResource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pri.yqx.ai.domain.entity.AiChat;
import pri.yqx.ai.domain.req.AiChatReq;
import pri.yqx.ai.domain.req.AigcReq;
import pri.yqx.ai.domain.req.AiSummaryReq;
import pri.yqx.ai.domain.vo.AiChatVo;
import pri.yqx.ai.service.AiChatService;
import pri.yqx.ai.service.adapter.AiChatAdapter;
import pri.yqx.common.common.ThreadHolder;
import pri.yqx.common.domain.response.Result;
import pri.yqx.common.util.MyBeanUtils;
import pri.yqx.good.domain.dto.GoodCursorReq;
import pri.yqx.good.domain.vo.GoodCursorPageVo;
import pri.yqx.good.service.GoodService;
import reactor.core.publisher.Flux;
import  cn.hutool.core.io.file.FileReader;
import java.io.*;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@RestController
@RequestMapping("/ai")
public class AiController {

    private final OpenAiChatModel chatModel;
    private final ChatClient chatClient;
    @Autowired
    private AiChatService aiChatService;

    @Value("${ai.json-file}")
    private String jsonFile;

    @Data
    static public class AiGood{
        private Long goodId;
        private String html;
        private String url;
        private BigDecimal price;
    }
    private void dbToJson(GoodService goodService) throws IOException {
        GoodCursorReq req = new GoodCursorReq();
        req.setCursor(0L);
        req.setPageSize(100);
        req.setDeviceWidth(375);
        req.setMaxHeight(500);
        GoodCursorPageVo pageVo = goodService.pageGoodVo(req);
        pageVo.getList().addAll(pageVo.getAnotherList());
        List<AiGood> collect = pageVo.getList().stream().map(i -> {
            AiGood aiGood = MyBeanUtils.copyProperties(i, new AiGood());
            aiGood.setUrl(i.getPicUrl().getUrl());
            return aiGood;
        }).collect(Collectors.toList());
        String s=JSON.toJSONString(collect);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(jsonFile),"UTF-8"));

        writer.write(s);
        writer.flush();
    }
    @Autowired
    public AiController(OpenAiChatModel chatModel, ChatClient.Builder chatClientBuilder, EmbeddingModel embeddingModel,GoodService goodService) throws IOException {
        this.chatModel = chatModel;
        SimpleVectorStore store = SimpleVectorStore.builder(embeddingModel).build();
        new Thread(()->{
            try {
                dbToJson(goodService);
            } catch (IOException e) {
                log.error("数据写入json文件异常");
                throw new RuntimeException(e);
            }
            JsonReader jsonReader = null;
            try {
                jsonReader = new JsonReader(new FileSystemResource(jsonFile));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            store.add(jsonReader.get());
        }).start();


//        new FileReader()
        this.chatClient=chatClientBuilder.defaultSystem(ResourceUtil.readUtf8Str("system_prompt.txt"))
                .defaultAdvisors(new QuestionAnswerAdvisor(store)).build();
//        InMemoryChatMemory memory = new InMemoryChatMemory();
//        memory.add("1",new UserMessage("我的名字叫熊大,请记住"));
//        memory.add("2",new AssistantMessage("好的,我已经记住了"));
//        this.chatClient=chatClientBuilder.build();
    }


    /**
     * 保存与ai的聊天消息
     * @param aiChatReq
     * @return
     */
    @PostMapping
    public Result<String> saveChatMsg(@RequestBody @Validated AiChatReq aiChatReq){
        aiChatService.saveChat(ThreadHolder.get(), aiChatReq);
        return Result.success("保存成功");
    }

    /**
     * 获取与ai的聊天记录
     * @return
     */
    @GetMapping("/{roomId}")
    public Result<List<AiChatVo>> getAiChats(@PathVariable Long roomId){
       List<AiChat> list= aiChatService.getAiChats(roomId);
       List<AiChatVo> aiChatVos = AiChatAdapter.build(list);
       return Result.success(aiChatVos);
    }

    /**
     * aigc,流式返回
     * @param aigcReq
     * @return
     */
    @PostMapping("/generateStream")
    public Flux<String> generateStream(@RequestBody AigcReq aigcReq) {
//        ChatMemory memory = new InMemoryChatMemory();
        ArrayList<Message> messages = new ArrayList<>();
        aigcReq.getHistory().forEach(i-> {
            if(i.getType()==0)
                messages.add(new AssistantMessage(i.getContent()));
            else
                messages.add(new UserMessage(i.getContent()));
        });
        return chatClient.prompt(new Prompt(messages)).user(aigcReq.getMessage())
                .stream().content();
    }

}
