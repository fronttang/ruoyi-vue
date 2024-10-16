package com.ruoyi.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Indexed;

/**
 * @author fronttang
 * @date 2021/07/15
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Indexed
@EnableConfigurationProperties(HttpClientProperties.class)
@Import(HttpClientConfiguration.class)
public @interface EnableHttpClient {

}
