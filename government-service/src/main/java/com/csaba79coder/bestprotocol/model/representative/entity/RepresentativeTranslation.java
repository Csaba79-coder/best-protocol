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
