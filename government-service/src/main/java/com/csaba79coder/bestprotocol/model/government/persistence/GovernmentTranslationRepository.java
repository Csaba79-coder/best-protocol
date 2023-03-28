package com.csaba79coder.bestprotocol.model.government.persistence;

import com.csaba79coder.bestprotocol.model.government.entity.GovernmentTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GovernmentTranslationRepository extends JpaRepository<GovernmentTranslation, Long> {

    List<GovernmentTranslation> findGovernmentTranslationByLanguageShortNameAndGovernmentId(String languageShortName, Long governmentId);
    List<GovernmentTranslation> findGovernmentTranslationByLanguageShortName(String languageShortName);
    Optional<GovernmentTranslation> findGovernmentByNameContainsIgnoreCase(String name);
    Optional<GovernmentTranslation> findGovernmentTranslationById(Long id);
    GovernmentTranslation findByGovernmentIdAndLanguageShortName(Long governmentId, String languageShortName);
}
