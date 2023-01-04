package com.github.demo.domain.base;


import com.github.demo.constant.DateTimeFormatConstant;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(callSuper = true)
public abstract class DateTime {

    /**
     * DB 테이블 기본 값 설정
     * 생성일, 수정일
     */
    @CreatedDate
    @Column(updatable = false)
    @DateTimeFormat(pattern = DateTimeFormatConstant.DATE_TIME_FORMAT)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @DateTimeFormat(pattern = DateTimeFormatConstant.DATE_TIME_FORMAT)
    private LocalDateTime updatedAt;

}
