package com.csaba79coder.bestprotocol.model.menu.persistence;

import com.csaba79coder.bestprotocol.model.menu.entity.MenuTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuTranslationRepository extends JpaRepository<MenuTranslation, Long> {

    List<MenuTranslation> findByLanguageShortNameAndTranslationKey(@Param("language_short_name") String languageShortname, String translationKey);
}
