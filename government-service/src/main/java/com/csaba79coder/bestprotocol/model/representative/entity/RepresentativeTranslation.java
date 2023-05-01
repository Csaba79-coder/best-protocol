package com.csaba79coder.bestprotocol.model.representative.entity;

import com.csaba79coder.bestprotocol.model.base.entity.Identifier;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**

 An entity representing a translation of a {@link Representative} object. This entity contains fields for translating

 the name, secretariat, job title, address, country, note, and secret note of a representative.

 This entity is annotated with the {@code @Entity} annotation, indicating that it is an entity that can be persisted

 to a database. It is also annotated with the {@code @Table} annotation, indicating the name of the database table to

 which it is mapped. The entity is also annotated with the {@code @Embeddable} annotation, which indicates that it can

 be embedded within another entity. RepresentativeTranslation is embedded within the {@link Representative} entity.

 Identifier provides the id field inherited by this entity. Id is UUID.
 */
@Entity
@Getter
@Setter
@Embeddable
@Table(name = "representative_translation")
public class RepresentativeTranslation extends Identifier {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "representative_id")
    private Representative representative;

    @Column(name = "language_short_name")
    private String languageShortName;

    @Column(name = "name")
    private String name;

    @Column(name = "secretairat")
    private String secretairat;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "address")
    private String address;

    @Column(name ="country")
    private String country;

    @Column(name = "note")
    private String note;

    @Column(name = "secret_note")
    private String secretNote;
}
