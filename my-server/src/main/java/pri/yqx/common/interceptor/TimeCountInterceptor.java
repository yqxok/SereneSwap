package pri.yqx.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class TimeCountInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(TimeCountInterceptor.class);
    private final ThreadLocal<Long> map1 = new ThreadLocal();

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        this.map1.set(System.currentTimeMillis());
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        try {
            Long begin = (Long)this.map1.get();
            Thread thread = Thread.currentThread();
            Long end = System.currentTimeMillis();
            String method = request.getMethod();
            String requestURI = request.getRequestURI();
            log.info("请求{}-{}用时{}毫秒", new Object[]{requestURI, method, end - begin});
        } catch (Exception var13) {
            throw new RuntimeException(var13);
        } finally {
            this.map1.remove();
        }

    }
}