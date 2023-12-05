package com.aditiya.simple.common.base.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@MappedSuperclass
@Data
@EntityListeners(AuditingEntityListener.class)
public class AuditModel {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column
    @CreatedDate
    private LocalDateTime createdDate;

    @Column
    @CreatedBy
    private String createdBy;

    @Column
    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @Column
    @LastModifiedBy
    private String modifiedBy;
}