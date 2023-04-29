package com.csaba79coder.bestprotocol.model.menu.persistence;

import com.csaba79coder.bestprotocol.model.menu.entity.MenuTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for performing CRUD operations on {@link MenuTranslation} entities in the database.
 */
@Repository
public interface MenuTranslationRepository extends JpaRepository<MenuTranslation, Long> {

    /**
     * Returns a list of {@link MenuTranslation} entities that match the specified language short name and translation key.
     *
     * @param languageShortname the language short name to search for
     * @param translationKey the translation key to search for
     * @return a list of {@link MenuTranslation} entities that match the specified language short name and translation key
     */
    List<MenuTranslation> findByLanguageShortNameAndTranslationKey(@Param("language_short_name") String languageShortname, String translationKey);
}
