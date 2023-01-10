package com.github.demo.global.scheduler.job;

import com.github.demo.domain.user.member.domain.Member;
import com.github.demo.domain.user.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class CustomJobConfig extends DefaultBatchConfiguration {

    private final MemberService memberService;

    @Value("${chunkSize:1000}")
    private int getChunkSize;

    @Bean
    public Job job(final JobRepository jobRepository, final Step sendSmsStackStep) {
        return new JobBuilder("job", jobRepository)
                .start(sendSmsStackStep)
                .build();
    }

    @Bean
    public Step step(final JobRepository jobRepository,
                     final PlatformTransactionManager transactionManager) {
        return new StepBuilder("step", jobRepository)
                .chunk(this.getChunkSize, transactionManager)
                .reader(this.memberReader())
                .writer(this.memberWriter())
                .build();
    }

    private ListItemReader<Member> memberReader() {
        return new ListItemReader<>(this.memberService.getAllMember());
    }

    @Bean
    public ItemWriter<Object> memberWriter() {
        return this::loggingAll;
    }

    private void loggingAll(final Chunk<?> objects) {
        objects.getItems().forEach(item -> log.info(item.toString()));
    }


}
