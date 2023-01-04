package com.github.demo.domain.base;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Embeddable
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Email implements Serializable {

    @Serial
    private static final long serialVersionUID = 3249234589483947123L;

    /**
     * 이메일
     */
    @jakarta.validation.constraints.Email(message = "{email_regexp}")
    @Column(name = "eamil", length = 50)
    @NotEmpty
    private String value;

    private static final String AT = "@";

    /**
     * 이메일 생성 빌더
     */
    public static Email of(final String value) {
        return Email.builder().value(value).build();
    }

    /**
     * 이메일 도메인 추출
     */
    public String getHost() {
        final int index = value.indexOf(AT);
        return index == -1 ? null : value.substring(index + 1);
    }

    /**
     * 이메일 @ 앞 추출
     */
    public String getId() {
        final int index = value.indexOf(AT);
        return index == -1 ? null : value.substring(0, index);
    }
}
