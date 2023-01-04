package com.github.demo.domain.base;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Where;

import java.util.UUID;

@Getter
@MappedSuperclass
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "deleted = false")
@ToString(callSuper = true)
public abstract class BaseEntity extends DateTime {

    @Id
    @Builder.Default
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 16, updatable = false, nullable = false)
    private UUID id = UUID.randomUUID();

    /**
     * 논리 삭제
     * 보여 주지 않으려 할때 사용 ex) 탈퇴한 회원
     */
    @Column(name = "deleted", nullable = false)
    @Builder.Default
    private Boolean deleted = false;

    public void deleted() {
        this.deleted = true;
    }
}
