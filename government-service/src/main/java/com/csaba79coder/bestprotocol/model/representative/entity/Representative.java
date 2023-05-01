package com.csaba79coder.bestprotocol.model.representative.entity;

import com.csaba79coder.bestprotocol.model.base.entity.Auditable;
import com.csaba79coder.bestprotocol.model.government.entity.Government;
import com.csaba79coder.bestprotocol.model.government.entity.PreviousJobTitleTranslation;
import com.csaba79coder.bestprotocol.model.value.Availability;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

/**

 An entity class that represents a representative. {@link Auditable} Extends from Identifier that represents the

 auto generated UUID for the entity. Extends {@link Auditable} and includes common audit fields such as

 created_at, updated_at, created_by and updated_by. This class is annotated with @Entity and @Table to specify the

 corresponding database table. It also includes a relationship with the {@link Government} entity, and has multiple

 one-to-many relationships with the {@link RepresentativeTranslation} and {@link PreviousJobTitleTranslation} entities.

 The class has fields for the representative's phone number, email, image, availability, and language short name.
 */
@Entity
@Table(name = "representative")
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "availability != 'DELETED'")
public class Representative extends Auditable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "government_id")
    private Government government;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "representative")
    private List<RepresentativeTranslation> fieldTranslations = new ArrayList<>();

    @OneToMany(mappedBy = "representative", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PreviousJobTitleTranslation> previousJobTitleTranslations = new ArrayList<>();

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Lob
    @Column(name = "image",length = Integer.MAX_VALUE, nullable = false)
    private byte[] image;


    @Enumerated(EnumType.STRING)
    @Column(name = "availability")
    private Availability availability = Availability.AVAILABLE;

    @Column(name = "language_short_name")
    private String languageShortName = null;
}
