package pri.yqx.common.serialize;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pri.yqx.common.interceptor.GlobalInterceptor;
import pri.yqx.common.interceptor.TimeCountInterceptor;
import pri.yqx.common.interceptor.TokenInterceptor;
import pri.yqx.common.serialize.LocalDateSerialier;
import pri.yqx.common.serialize.LocalDateTimeSerializer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    public static String[] ALLOW_Path = new String[]{"/user/no/**", "/download/**", "/good/no/**", "/goodComment/no/**", "/category/no/**"};

    public WebMvcConfig() {
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new GlobalInterceptor()).addPathPatterns(new String[]{"/**"});
        registry.addInterceptor(new TokenInterceptor()).addPathPatterns(new String[]{"/**"}).excludePathPatterns(new String[]{"/*/no/**"});
        registry.addInterceptor(new TimeCountInterceptor()).addPathPatterns(new String[]{"/**"});
    }

    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(new SerializerFeature[]{SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullBooleanAsFalse, SerializerFeature.DisableCircularReferenceDetect});
        SerializeConfig serializeConfig = SerializeConfig.globalInstance;
        serializeConfig.put(Long.class, ToStringSerializer.instance);
        serializeConfig.put(Long.TYPE, ToStringSerializer.instance);
        serializeConfig.put(LocalDateTime.class, new LocalDateTimeSerializer());
        serializeConfig.put(LocalDate.class, new LocalDateSerialier());
        fastJsonConfig.setSerializeConfig(serializeConfig);
        converter.setFastJsonConfig(fastJsonConfig);
        converter.setDefaultCharset(StandardCharsets.UTF_8);
        List<MediaType> mediaTypeList = new ArrayList();
        mediaTypeList.add(MediaType.APPLICATION_JSON);
        converter.setSupportedMediaTypes(mediaTypeList);
        converters.add(0, converter);
    }
}