package com.github.demo.domain.base;

import com.github.demo.constant.RegexConstant;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;


@Embeddable
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Phone implements Serializable {

    @Serial
    private static final long serialVersionUID = 192372340983452L;


    /**
     * 전화번호
     */
    @Column(name = "phone", length = 20)
    @NotEmpty
    private String value;

    /**
     * 전화번호 생성 빌더
     */
    public static Phone of(final String phone) {
        return Phone.builder().value(phone).build();
    }

    /**
     * 마지막 전화번호4자리 얻기 위해
     * 조건 해당하지 않는 경우 앞에 국제화 + 제거 위해 substring(1)
     *
     * @return 추출한 4자리 or + 제외한 숫자
     */
    public int getLastPhoneNumber() {

        final int phoneLength = this.value.length();
        final int wantLength = 4;

        return phoneLength > wantLength ?
                Integer.parseInt(this.value.substring(phoneLength - wantLength))
                : Integer.parseInt(this.value);
    }

    /**
     * 뒷 4자리 String 리턴
     *
     * @return string
     */
    public String getCode() {
        return String.format(RegexConstant.LAST_NUMBER, this.getLastPhoneNumber());
    }
}
