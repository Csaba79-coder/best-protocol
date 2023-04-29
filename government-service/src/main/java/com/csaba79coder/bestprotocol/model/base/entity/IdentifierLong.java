package com.csaba79coder.bestprotocol.model.base.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

/**

 A base class for entities that have a Long ID as their primary key.

 Provides an id field annotated with @Id, @GeneratedValue, and @Column annotations.
 */
@MappedSuperclass
@Getter
public class IdentifierLong {

    /**

     The Long ID of the entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
}
