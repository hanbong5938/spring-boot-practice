package com.github.demo.global.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Configuration;

@EnableBatchProcessing
//@EnableBatchProcessing(tablePrefix = "batch.BATCH_")
@Configuration
public class BatchConfig {
}
