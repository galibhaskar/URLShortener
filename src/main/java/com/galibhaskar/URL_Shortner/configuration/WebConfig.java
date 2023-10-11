package com.galibhaskar.URL_Shortner.configuration;

import com.galibhaskar.URL_Shortner.interceptors.PremiumURLInterceptor;
import com.galibhaskar.URL_Shortner.interceptors.URLInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final URLInterceptor urlInterceptor;
    private final PremiumURLInterceptor premiumURLInterceptor;

    public WebConfig(@Autowired URLInterceptor urlInterceptor,
                     @Autowired PremiumURLInterceptor premiumURLInterceptor) {
        this.urlInterceptor = urlInterceptor;
        this.premiumURLInterceptor = premiumURLInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(urlInterceptor).addPathPatterns("/*").order(1);

        registry.addInterceptor(premiumURLInterceptor).addPathPatterns("/premium/create").order(2);
    }
}
