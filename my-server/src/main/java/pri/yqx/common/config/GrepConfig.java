package pri.yqx.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pri.yqx.sensitive.GrepFileLoader;
import pri.yqx.sensitive.SensitiveWordConverter;

import java.util.List;

@Configuration
public class GrepConfig {
    /**
     * 敏感词过滤转换器
     * @return
     */
    @Bean
    public SensitiveWordConverter sensitiveWordConverter(){
        List<String> list = GrepFileLoader.loadGrepFile();
        return new SensitiveWordConverter(list);
    }
}
