package pri.yqx.ai;
//
import lombok.Data;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.reader.JsonReader;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pri.yqx.good.domain.entity.Good;
import pri.yqx.good.service.GoodService;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class AiController {

    private final OpenAiChatModel chatModel;
    private final ChatClient chatClient;
    @Data
    static public class AiGood{
        private Long goodId;
        private String html;
        private String url;
        private BigDecimal price;
    }
    @Autowired
    public AiController(OpenAiChatModel chatModel, ChatClient.Builder chatClientBuilder, EmbeddingModel embeddingModel) {
        this.chatModel = chatModel;
        SimpleVectorStore store = SimpleVectorStore.builder(embeddingModel).build();
        new Thread(()->{
            JsonReader jsonReader = new JsonReader(new ClassPathResource("data.json"));
            store.add(jsonReader.get());
        }).start();

        this.chatClient=chatClientBuilder.defaultSystem( "不需要md格式的回复\n"+
                        "你扮演两个角色,一个是用户的朋友,可以和用户正常聊天,另一个是闲鱼平台的助手,\n" +
                        "如果用户想要买东西,你要帮助用户找到他们提出需要的闲置商品,默认推荐5个匹配度最高的商品\n"+
                        "你需要把你的回复转化为json格式,格式如下:{message:这是你的回复,items:这是你给用户推荐的商品列表},items格式为{goodId:这是商品id,price:这是商品价格,url:这是商品封面,html:这是商品描述},items的属性值不能是虚构的并且属性值内容要与文档查询出来的一致")
                .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()),
                        new QuestionAnswerAdvisor(store)).build();
    }

    @GetMapping("/ai/generate")
    public String generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return this.chatClient.prompt().user(message).call().content();
    }

    @GetMapping( value = "/ai/generateStream")
    public Flux<String> generateStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {

        return chatClient.prompt().user(message)
                .stream().content();
    }
}
