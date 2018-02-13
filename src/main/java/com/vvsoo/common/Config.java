package com.vvsoo.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix="config",ignoreUnknownFields=false)
@PropertySource("classpath:config/config.properties")
@Data
@Component
public class Config {
	private String version;
	private String url;
	private String localurl;
}
