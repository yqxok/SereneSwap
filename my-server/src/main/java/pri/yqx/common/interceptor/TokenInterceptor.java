package pri.yqx.common.interceptor;



import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import pri.yqx.common.common.ThreadHolder;
import pri.yqx.common.util.JwtUtil;

public class TokenInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(TokenInterceptor.class);
    private static final String TOKEN_HEADER = "token";


    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");

        try {
            Long userId = JwtUtil.verifyAndGetId(token);
            ThreadHolder.set(userId);
            return true;
        } catch (RuntimeException var6) {
            log.warn("接口{},token校验失败", request.getRequestURI());
            return false;
        }
    }
}