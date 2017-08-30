package com.valentichu.server.base.configurer;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.valentichu.server.base.security.interceptor.CookieRefreshInterceptor;
import com.valentichu.server.base.security.interceptor.TokenValidateInterceptor;
import org.apache.tomcat.util.http.LegacyCookieProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Spring MVC 配置
 *
 * @author Valentichu
 * created on 2017/08/25
 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {
    /**
     * 装载是否使用Cookie中的Token验证
     */
    @Value("${jwt.enableCookie}")
    private boolean enableCookie;

    /**
     * 装载Token认证拦截器
     */
    private final TokenValidateInterceptor tokenValidateInterceptor;
    /**
     * 装载Cookie处理Token的拦截器
     */
    private final CookieRefreshInterceptor cookieRefreshInterceptor;

    @Autowired
    public WebMvcConfigurer(TokenValidateInterceptor tokenValidateInterceptor, CookieRefreshInterceptor cookieRefreshInterceptor) {
        this.tokenValidateInterceptor = tokenValidateInterceptor;
        this.cookieRefreshInterceptor = cookieRefreshInterceptor;
    }

    /**
     * 更改Tomcat容器设置：返回的Cookie允许空格
     */
    @Bean
    public EmbeddedServletContainerCustomizer customizer() {
        return container -> {
            if (container instanceof TomcatEmbeddedServletContainerFactory) {
                TomcatEmbeddedServletContainerFactory tomcat = (TomcatEmbeddedServletContainerFactory) container;
                tomcat.addContextCustomizers(context -> context.setCookieProcessor(new LegacyCookieProcessor()));
            }
        };
    }

    /**
     * 使用阿里 FastJson 作为JSON MessageConverter
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(SerializerFeature.WriteMapNullValue,//保留空的字段
                SerializerFeature.WriteNullStringAsEmpty,//String null -> ""
                SerializerFeature.WriteNullNumberAsZero);//Number null -> 0
        converter.setFastJsonConfig(config);
        List<MediaType> list = new ArrayList<>();
        list.add(MediaType.APPLICATION_JSON_UTF8);
        converter.setSupportedMediaTypes(list);
        converters.add(converter);
    }

    /**
     * 添加拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //Token认证拦截器,参数为不需要认证是否登录的域(swagger-resource路径下存放swagger2相关信息，不需要认证)
        registry.addInterceptor(tokenValidateInterceptor).excludePathPatterns("/swagger-resources/**", "/auth/**", "/goods");
        //如果允许使用Cookie,自动更新Cookie中的Token，参数为不需要自动更新Cookie中的Token的域
        if (enableCookie) {
            registry.addInterceptor(cookieRefreshInterceptor).excludePathPatterns("/swagger-resources/**", "/auth/**", "/goods");
        }

    }

    /**
     * 解决跨域问题
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //registry.addMapping("/**");
    }

}
