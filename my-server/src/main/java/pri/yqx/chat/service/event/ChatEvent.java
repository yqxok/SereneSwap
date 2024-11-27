package pri.yqx.chat.service.event;

import org.springframework.context.ApplicationEvent;

public class ChatEvent extends ApplicationEvent {
    private Long chatId;

    public ChatEvent(Object source, Long chatId) {
        super(source);
        this.chatId = chatId;
    }

    public Long getChatId() {
        return this.chatId;
    }
}