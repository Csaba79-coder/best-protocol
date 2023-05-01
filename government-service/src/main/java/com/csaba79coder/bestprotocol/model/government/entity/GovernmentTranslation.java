package com.csaba79coder.bestprotocol.model.government.entity;

import com.csaba79coder.bestprotocol.model.base.entity.IdentifierLong;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**

 Represents a translation of a {@link Government} entity in a specific language.
 id inherited from IdentifierLong.
 */
@Entity
@Getter
@Setter
@Table(name = "government_translation")
public class GovernmentTranslation extends IdentifierLong {

    @ManyToOne
    @JoinColumn(name = "government_id")
    private Government government;

    @Column(name = "language_short_name")
    private String languageShortName;

    @Column(name = "name")
    private String name;
}