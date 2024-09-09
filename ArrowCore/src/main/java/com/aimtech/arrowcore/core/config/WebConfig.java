package com.aimtech.arrowcore.core.config;

import com.aimtech.arrowcore.infrastructure.converter.CaseInsensitiveFilterStatusEnumConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new CaseInsensitiveFilterStatusEnumConverter());
    }

}
