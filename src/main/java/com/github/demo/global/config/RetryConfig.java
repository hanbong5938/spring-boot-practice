package com.github.demo.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.Retryable;

@Retryable
@Configuration
public class RetryConfig {
}
