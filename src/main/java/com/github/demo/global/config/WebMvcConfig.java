package com.github.demo.global.config;


import com.github.demo.global.web.resolver.IpAddressArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

//    @Override
//    public void addInterceptors(final InterceptorRegistry registry) {
////        registry.addInterceptor(testInterceptor)
////                .addPathPatterns("/test");
//    }

    @Value("${cors.allowed-origins}")
    private String[] cors;

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(cors)
                .allowedMethods(HttpMethod.GET.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.HEAD.name(),
                        HttpMethod.DELETE.name(),
                        HttpMethod.PUT.name())
                .allowCredentials(true);
    }

    @Override
    public void addArgumentResolvers(final @NotNull List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new IpAddressArgumentResolver());
    }
}

