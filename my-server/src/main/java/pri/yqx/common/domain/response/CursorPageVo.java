

package pri.yqx.common.domain.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class CursorPageVo<T> implements Serializable {
    private Long cursor;
    private Boolean isEnd;
    private Integer size;
    private List<T> list;

    public CursorPageVo(){}
    public CursorPageVo(final Long cursor, final Boolean isEnd, final Integer size, final List<T> list) {
        this.cursor = cursor;
        this.isEnd = isEnd;
        this.size = size;
        this.list = list;
    }
}
