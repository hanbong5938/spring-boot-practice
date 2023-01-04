package com.github.demo.domain.user.member.domain;

import com.github.demo.domain.base.BaseEntity;
import com.github.demo.domain.user.common.domain.Account;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends Account implements Serialwizable {
    @Serial
    private static final long serialVersionUID = 32409238471221L;

}
