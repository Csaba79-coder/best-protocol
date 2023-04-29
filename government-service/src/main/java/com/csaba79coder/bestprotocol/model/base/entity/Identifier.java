package com.csaba79coder.bestprotocol.model.base.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import java.util.UUID;

/**
 * A base class for entities that have an ID field. This class provides a unique ID field which is generated using the
 * UUID strategy.
 *
 * This class is annotated with @MappedSuperclass which means that the properties of this class will be mapped to the
 * properties of its subclasses.
 */
@MappedSuperclass
@Getter
public class Identifier {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;
}
