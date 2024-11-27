//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.comment.service.event;

import org.springframework.context.ApplicationEvent;

public class CommentEvent extends ApplicationEvent {
    private Long commentId;
    private Integer type;

    public CommentEvent(Object source, Long commentId, Integer type) {
        super(source);
        this.commentId = commentId;
        this.type = type;
    }

    public Long getCommentId() {
        return this.commentId;
    }

    public Integer getType() {
        return this.type;
    }
}
