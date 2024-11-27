package pri.yqx.common.constant;

public class RedisKey {
    public static final String BASE_KEY = "wx-app:";
    public static final String USER_INFO = "userInfo";
    public static final String LATEST_GOOD_ID = "latest_good_id";
    public static final String GOOD_INFO = "good_info";


    public static String getRedisKey(String prefix, Object key) {
        return "wx-app:" + prefix + "::" + key;
    }
}