package com.spring.hellobatch.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j // log 사용
@RequiredArgsConstructor // 생성자 DI
@Configuration //Spring Batch의 모든 Job은 @Configuration 사용
public class SimpleJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job simpleJob() {
        return jobBuilderFactory.get("simpleJob") // simpleJob이라는 Job을 생성하겠다
                .start(simpleStep1())
                .build();
    }

    /***
     * Tasklet 하나와 Reader & Processor & Writer 한 묶음이 같은 레벨이다.
     * 그래서 Reader & Processor가 끝나고 Tasklet으로 마무리 짓는 등으로 만들 수 없다.
     */
    @Bean
    public Step simpleStep1() {
        return stepBuilderFactory.get("simpleStep1") // simpleStep1이라는 Step을 생성하겠다
                .tasklet(((contribution, chunkContext) -> { // Step안에서 단일로 수행될 커텀한 기능들을 선언할 때 사용
                    log.info(">>>>> This is Step1");
                    return RepeatStatus.FINISHED;
                }))
                .build();
    }
}