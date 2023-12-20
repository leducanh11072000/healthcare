package com.example.healthcareapplication.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {
    @Column(name = "create_time")
    @CreatedDate
    protected LocalDateTime createTime;

    @Column(name = "create_by")
    @CreatedBy
    protected String createBy;

    @Column(name = "update_time")
    @LastModifiedDate
    protected LocalDateTime updateTime;

    @Column(name = "update_by")
    @LastModifiedBy
    protected String updateBy;

    @Column(name = "status")
    protected Long status;
}
