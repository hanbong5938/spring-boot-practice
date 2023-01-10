package com.github.demo.global.scheduler;

import lombok.RequiredArgsConstructor;
import org.aspectj.util.SoftHashMap;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class JobScheduler {

    private final Job job;

    private final JobLauncher jobLauncher;

    @Scheduled(cron = "0 0 9 * * *", zone = "Asia/Seoul")
    public void nine() {
        this.schedulerRun(this.job);
    }


    private void schedulerRun(final Job job) {
        final Map<String, JobParameter<?>> stringJobParameterMap = new SoftHashMap<>();
        stringJobParameterMap.put("time", new JobParameter<>(System.currentTimeMillis(), Long.class));
        final JobParameters jobParameters = new JobParameters(stringJobParameterMap);
        try {
            this.jobLauncher.run(job, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException | JobRestartException e) {
            e.printStackTrace();
        }
    }
}
