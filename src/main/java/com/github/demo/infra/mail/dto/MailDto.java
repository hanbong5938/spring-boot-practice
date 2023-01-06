package com.github.demo.infra.mail.dto;

import com.github.demo.domain.user.common.domain.Account;
import lombok.Builder;

public record MailDto() {
    public record MailResponse(String message) {
    }

    @Builder
    public record MailRequest(Account account, String subject, String text) {
        public static MailRequest of(final Account account, final String subject, final String text) {
            return MailRequest.builder().account(account).subject(subject).text(text).build();
        }
    }

}
