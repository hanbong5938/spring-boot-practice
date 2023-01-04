package com.github.demo.domain.user.admin.domain;

import com.github.demo.domain.user.common.domain.Account;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class Admin extends Account implements Serializable {

    @Serial
    private static final long serialVersionUID = 3249224545894147123L;

}
