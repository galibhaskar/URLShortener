package com.galibhaskar.URL_Shortner.configuration;

import com.galibhaskar.URL_Shortner.interceptors.URLInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    final
    URLInterceptor urlInterceptor;

    public WebConfig(@Autowired URLInterceptor urlInterceptor) {
        this.urlInterceptor = urlInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(urlInterceptor).addPathPatterns("/premium/create");
        registry.addInterceptor(urlInterceptor);
    }
}
