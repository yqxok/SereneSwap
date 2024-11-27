//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.common.service.cache;

import java.util.Map;
import java.util.Set;

public interface MutiCache<T> {
    Map<Long, T> getCacheMap(Set<Long> ids, QueryHandler<T> handler);
}
