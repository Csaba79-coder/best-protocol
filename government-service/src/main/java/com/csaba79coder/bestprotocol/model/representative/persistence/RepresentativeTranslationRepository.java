package com.csaba79coder.bestprotocol.model.representative.persistence;

import com.csaba79coder.bestprotocol.model.representative.entity.RepresentativeTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**

 This repository interface extends JpaRepository for CRUD operations on RepresentativeTranslation entities.

 It also provides a method to find a RepresentativeTranslation by language short name and representative ID.
 */
@Repository
public interface RepresentativeTranslationRepository extends JpaRepository<RepresentativeTranslation, Long> {
    RepresentativeTranslation findRepresentativeTranslationByLanguageShortNameAndRepresentativeId(@Param("language_short_name") String languageShortname, UUID id);
}
