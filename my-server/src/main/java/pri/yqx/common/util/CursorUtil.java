//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.common.util;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import pri.yqx.common.domain.response.CursorPageVo;

public class CursorUtil {
    public CursorUtil() {
    }

    public static <T> CursorPageVo<T> getCursorPage() {
        return null;
    }

    public static <T> Page<T> getPage(Integer pageSize) {
        return new Page(1L, (long)pageSize, false);
    }

    public static Long buildCursor(Boolean check, Long cursor, Supplier<Long> supplier) {
        return check ? (Long)supplier.get() : cursor;
    }

    public static <T> CursorPageVo<T> init(IService<T> service, Integer pageSize, Consumer<LambdaQueryChainWrapper<T>> consumer, Function<T, Long> function) {
        LambdaQueryChainWrapper<T> lambdaQuery = service.lambdaQuery();
        consumer.accept(lambdaQuery);
        Page<T> page = lambdaQuery.page(getPage(pageSize + 1));
        if (CollectionUtil.isEmpty(page.getRecords())) {
            return newCursorPageVo(0L, true, new ArrayList());
        } else {
            Boolean isEnd = pageSize + 1 != page.getRecords().size();
            List<T> list1 = page.getRecords();
            Long newCusor = !list1.isEmpty() ? (Long)function.apply(CollectionUtil.getLast(list1)) : null;
            if (list1.size() > pageSize) {
                list1.remove(list1.size() - 1);
            }

            return newCursorPageVo(isEnd ? null : newCusor, isEnd, list1);
        }
    }

    public static <T> CursorPageVo<T> newCursorPageVo(Long cursor, Boolean isEnd, List<T> list) {
        return new CursorPageVo(cursor, isEnd, CollectionUtil.size(list), list);
    }

    public static <T1, T2> CursorPageVo<T2> copyCursorPage(CursorPageVo<T1> p1, List<T2> collect) {
        return newCursorPageVo(p1.getCursor(), p1.getIsEnd(), collect);
    }

    public static <T> CursorPageVo<T> combineCursorPage(List<T> list, Integer pageSize, Function<T, Long> function) {
        if (CollectionUtil.isEmpty(list)) {
            return new CursorPageVo(0L, true, 0, new ArrayList());
        } else {
            boolean isEnd = list.size() != pageSize + 1;
            Long cursor = (Long)function.apply(CollectionUtil.getLast(list));
            return new CursorPageVo(cursor, isEnd, list.size(), list);
        }
    }
}
