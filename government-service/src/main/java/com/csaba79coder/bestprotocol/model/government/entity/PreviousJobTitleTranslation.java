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

/**

 Entity class representing the previous job title translation.

 It has a many-to-one relationship with the {@link Representative} entity and stores the translations for the previous job title field in different languages.
 id inherited from IdentifierLong.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "previous_job_title_translation")
public class PreviousJobTitleTranslation extends IdentifierLong {

    /**

     The representative entity that this translation belongs to.
     */
    @ManyToOne
    @JoinColumn(name = "representative_id")
    private Representative representative;

    /**

     The short name of the language for this translation.
     */
    @Column(name = "language_short_name")
    private String languageShortName;

    /**

     The translated value of the previous job title field in the language represented by {@link #languageShortName}.
     */
    @Column(name = "name")
    private String name;
}
