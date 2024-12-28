package pri.yqx;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.evaluation.EvaluationRequest;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class ChatController {

    private final OpenAiChatModel chatModel;
    private final ChatClient chatClient;

    @Autowired
    public ChatController(OpenAiChatModel chatModel, ChatClient.Builder chatClientBuilder,EmbeddingModel embeddingModel) {
        this.chatModel = chatModel;
//        SimpleVectorStore store = SimpleVectorStore.builder(embeddingModel).build();
//
//        store.add(List.of(Document.builder()
//                .text(text)
//                .metadata("artical", "微信小程序流数据")
//                .build()));
        this.chatClient=chatClientBuilder.defaultSystem("你是一个二手平台的助手,除了正常聊天外还需要帮助用户找到他们提出需要的闲置商品,默认推荐5个匹配度最高的商品," +
                        "你需要把你的回复转化为json格式,格式如下:{'message':这是你的回复,items:这是你给用户推荐的商品列表},items格式为{goodId:这是商品id,price:这是商品价格,html:这是商品描述}")
                .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory())
                        ).build();
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
