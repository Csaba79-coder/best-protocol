package com.csaba79coder.bestprotocol.model.menu.entity;

import com.csaba79coder.bestprotocol.model.base.entity.IdentifierLong;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "menu_translation")
public class MenuTranslation extends IdentifierLong {

    @Column(name = "translation_key", nullable = false)
    private String translationKey;

    @Column(name = "language_short_name", nullable = false)
    private String languageShortName;

    @Column(name = "translation_value", nullable = false)
    private String translationValue;
}
