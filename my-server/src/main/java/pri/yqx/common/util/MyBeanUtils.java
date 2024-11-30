//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.common.util;

import cn.hutool.core.collection.CollectionUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;

public class MyBeanUtils {

    public static <M> M copyProperties(Object source, M target) {
        if (Objects.isNull(source)) {
            return target;
        } else {
            BeanUtils.copyProperties(source, target);
            return target;
        }
    }

    public static <T, R> List<R> getPropertyList(Collection<T> collection, Function<T, R> function) {
        return CollectionUtil.isEmpty(collection) ? new ArrayList<>() : collection.stream().map(function).collect(Collectors.toList());
    }

    public static <T, R> Set<R> getPropertySet(Collection<T> collection, Function<T, R> function) {
        return CollectionUtil.isEmpty(collection) ? new HashSet<>() : collection.stream().map(function).collect(Collectors.toSet());
    }

    public static <T> Map<Long, T> transMap(Set<T> set, Function<T, Long> function) {
        return CollectionUtil.isEmpty(set) ? new HashMap<>() : set.stream().collect(Collectors.toMap(function, Function.identity()));
    }

    public static <T, R> List<Set<R>> getPropertySetList(Collection<T> collection, Function<T, R>... function) {
        ArrayList<Set<R>> list = new ArrayList<>();
        for(int i = 0; i < function.length; ++i) {
            list.add(new HashSet<>());
        }
        for (T item : collection) {
            for(int j = 0; j < list.size(); ++j) {
                list.get(j).add(function[j].apply(item));
            }
        }
        return list;
    }
}
