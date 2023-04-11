package com.csaba79coder.bestprotocol.model.representative.persistence;

import com.csaba79coder.bestprotocol.model.representative.entity.RepresentativeTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RepresentativeTranslationRepository extends JpaRepository<RepresentativeTranslation, Long> {
    List<RepresentativeTranslation> findRepresentativeTranslationByLanguageShortName(@Param("language_short_name") String languageShortname);
    RepresentativeTranslation findRepresentativeTranslationByLanguageShortNameAndRepresentativeId(@Param("language_short_name") String languageShortname, UUID id);
}
