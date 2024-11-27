
package pri.yqx.common.common;

public class ThreadHolder {
    private static final ThreadLocal<Long> threadLocal = new ThreadLocal();

    public ThreadHolder() {
    }

    public static void set(Long userId) {
        threadLocal.set(userId);
    }

    public static Long get() {
        return (Long)threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }
}
