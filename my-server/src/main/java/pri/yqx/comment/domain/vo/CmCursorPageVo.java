
package pri.yqx.comment.domain.vo;

import cn.hutool.core.collection.CollectionUtil;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pri.yqx.common.domain.response.CursorPageVo;
@Getter
@Setter
@NoArgsConstructor
public class CmCursorPageVo<T> extends CursorPageVo<T> {
    private Boolean showEnd;

    public CmCursorPageVo(Long cursor, Boolean isEnd, List<T> list, Integer size, Boolean showEnd) {
        super(cursor, isEnd, size, list);
        this.showEnd = showEnd;
    }
    public CmCursorPageVo(CmCursorPageVo<?> cursorPageVo, List<T> list) {
        super(cursorPageVo.getCursor(), cursorPageVo.getIsEnd(), CollectionUtil.size(list), list);
        this.showEnd = cursorPageVo.getShowEnd();
    }
}
