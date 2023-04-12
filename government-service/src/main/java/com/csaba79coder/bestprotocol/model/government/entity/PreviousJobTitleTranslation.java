package com.csaba79coder.bestprotocol.model.government.entity;

import com.csaba79coder.bestprotocol.model.base.entity.IdentifierLong;
import com.csaba79coder.bestprotocol.model.representative.entity.Representative;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "previous_job_title_translation")
public class PreviousJobTitleTranslation extends IdentifierLong {

    @ManyToOne
    @JoinColumn(name = "representative_id")
    private Representative representative;

    @Column(name = "language_short_name")
    private String languageShortName;

    @Column(name = "name")
    private String name;
}
