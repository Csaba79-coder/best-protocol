package com.csaba79coder.bestprotocol.model.base.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
public class Auditable extends Identifier {

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    protected LocalDateTime createdAt = LocalDateTime.now();

    @CreationTimestamp
    @Column(name = "updated_at", nullable = false)
    protected LocalDateTime updatedAt = LocalDateTime.now();

    @Column(name = "created_by")
    protected UUID createdBy = UUID.fromString("6772c9dc-a7be-4826-963a-e376074fd4e7");

    @Column(name = "updated_by")
    protected UUID updatedBy = UUID.fromString("dbd58012-9ee7-47d5-8f87-9bbc91583009");
}
