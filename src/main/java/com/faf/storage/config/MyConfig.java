//package com.faf.storage.config;
//
//import jakarta.servlet.MultipartConfigElement;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class MyConfig {
//
//    @Bean
//    @ConditionalOnMissingBean({ MultipartConfigElement.class,
//            CommonsMultipartResolver.class })
//    public MultipartConfigElement multipartConfigElement() {
//        return this.multipartProperties.createMultipartConfig();
//    }
//}
