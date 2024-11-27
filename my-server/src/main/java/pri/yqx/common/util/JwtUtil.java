
package pri.yqx.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    private static final String SIGNATURE = "token!@#$%^7890";
    private static final Long LAST_TIME = 604800000L;

    public JwtUtil() {
    }

    public static String getToken(Map<String, String> map) {
        JWTCreator.Builder builder = JWT.create();
        map.forEach((k, v) -> {
            builder.withClaim(k, v);
        });
        builder.withExpiresAt(new Date((new Date()).getTime() + LAST_TIME));
        return builder.sign(Algorithm.HMAC256("token!@#$%^7890")).toString();
    }

    public static DecodedJWT verify(String token) {
        return JWT.require(Algorithm.HMAC256("token!@#$%^7890")).build().verify(token);
    }

    public static Long getUserId(String token) {
        DecodedJWT decode = JWT.decode(token);
        String userId = decode.getClaim("userId").asString();
        return Long.valueOf(userId);
    }

    public static String getToken(Long userId) {
        HashMap<String, String> map = new HashMap();
        map.put("userId", Long.toString(userId));
        return getToken((Map)map);
    }

    public static Long verifyAndGetId(String token) {
        DecodedJWT decodedJWT = verify(token);
        DecodedJWT decode = JWT.decode(token);
        String userId = decode.getClaim("userId").asString();
        return Long.valueOf(userId);
    }

    public static String updateToken(String token) {
        Long userId = verifyAndGetId(token);
        return getToken(userId);
    }
}
