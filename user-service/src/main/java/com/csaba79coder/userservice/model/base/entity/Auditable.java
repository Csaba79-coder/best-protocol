package com.csaba79coder.userservice.model.base.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * A base class for entities that need to be audited. This class provides the common audit fields such as created_at,
 * updated_at, created_by and updated_by.
 *
 * This class is annotated with @MappedSuperclass which means that the properties of this class will be mapped to the
 * properties of its subclasses.
 */
@MappedSuperclass
@Getter
@Setter
public class Auditable extends Identifier {

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @CreationTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(name = "created_by")
    private UUID createdBy = UUID.fromString("6772c9dc-a7be-4826-963a-e376074fd4e7");

    @Column(name = "updated_by")
    private UUID updatedBy = UUID.fromString("dbd58012-9ee7-47d5-8f87-9bbc91583009");
}
