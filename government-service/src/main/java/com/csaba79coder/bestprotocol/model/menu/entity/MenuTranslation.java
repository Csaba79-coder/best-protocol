package com.csaba79coder.bestprotocol.model.menu.entity;

import com.csaba79coder.bestprotocol.model.base.entity.IdentifierLong;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a translation for a menu item.
 * This entity is mapped to the "menu_translation" database table.
 * Extends the {@link IdentifierLong} class to inherit an auto-generated {@code Long} ID.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "menu_translation")
public class MenuTranslation extends IdentifierLong {

    /**

     The translation key of the menu item.
     */
    @Column(name = "translation_key", nullable = false)
    private String translationKey;

    /**

     The language short name of the menu item translation.
     */
    @Column(name = "language_short_name", nullable = false)
    private String languageShortName;

    /**

     The value of the menu item translation.
     */
    @Column(name = "translation_value", nullable = false)
    private String translationValue;
}
