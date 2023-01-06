package com.github.demo.infra.mail.event.handler;

import com.github.demo.infra.mail.dto.MailDto;
import com.github.demo.infra.mail.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class GatewayHandler {
    private final MailService mailService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void send(final MailDto.MailRequest request) {
        this.mailService.send(request);
    }



}
