package com.github.demo.infra.mail.client;

import com.github.demo.infra.mail.dto.MailDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

@Component
public class MailClient {

    private final WebClient webClient;

    /**
     * 웹 클라이언트 생성
     */
    public MailClient(final WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://api.url//").build();
    }

    /**
     * x-secrutity 입력하여 전송
     * 재시도 5회까지
     * url + 인식하지 못하는 경우 방지 위하여
     */
    public MailDto.MailResponse sendSms(final MailDto.MailRequest request) {
        return this.webClient.post()
                .uri(uriBuilder -> UriComponentsBuilder.fromUri(uriBuilder.build())
                        .path("/mail")
                        .encode()
                        .buildAndExpand()
                        .toUri())
                .headers(headers -> {
                    headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                    headers.add("x-secrutity", "emwfeudn32");
                })
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), MailDto.MailRequest.class)
                .retrieve()
                .bodyToMono(MailDto.MailResponse.class)
                .retryWhen(Retry.max(5))
                .publishOn(Schedulers.boundedElastic())
                .doOnError(Throwable::printStackTrace)
                .block();
    }
}
