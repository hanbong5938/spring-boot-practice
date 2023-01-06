package com.github.demo.infra.mail.service;

import com.github.demo.infra.mail.client.MailClient;
import com.github.demo.infra.mail.dto.MailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final MailClient mailClient;

    public MailDto.MailResponse send(final MailDto.MailRequest request) {
        return this.mailClient.send(request);
    }

}
