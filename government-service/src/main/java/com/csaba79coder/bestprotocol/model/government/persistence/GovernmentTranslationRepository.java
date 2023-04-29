package com.csaba79coder.bestprotocol.model.government.persistence;

import com.csaba79coder.bestprotocol.model.government.entity.GovernmentTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the {@link GovernmentTranslation} entity.
 */
@Repository
public interface GovernmentTranslationRepository extends JpaRepository<GovernmentTranslation, Long> {

    /**
     * Finds all {@link GovernmentTranslation}s with the given language short name and government ID.
     *
     * @param languageShortName the language short name to search for
     * @param governmentId      the ID of the government to search for
     * @return a list of matching government translations, or an empty list if none found
     */
    List<GovernmentTranslation> findGovernmentTranslationByLanguageShortNameAndGovernmentId(String languageShortName, Long governmentId);
    /**
     * Finds all {@link GovernmentTranslation}s with the given language short name.
     *
     * @param languageShortName the language short name to search for
     * @return a list of matching government translations, or an empty list if none found
     */
    List<GovernmentTranslation> findGovernmentTranslationByLanguageShortName(String languageShortName);
    /**
     * Finds the {@link GovernmentTranslation} with a name containing the given string (case-insensitive).
     *
     * @param name the string to search for
     * @return an optional containing the matching government translation, or an empty optional if none found
     */
    Optional<GovernmentTranslation> findGovernmentByNameContainsIgnoreCase(String name);
    /**
     * Finds the {@link GovernmentTranslation} with the given ID.
     *
     * @param id the ID to search for
     * @return an optional containing the matching government translation, or an empty optional if none found
     */
    Optional<GovernmentTranslation> findGovernmentTranslationById(Long id);
    /**
     * Finds the {@link GovernmentTranslation} with the given government ID and language short name.
     *
     * @param governmentId      the ID of the government to search for
     * @param languageShortName the language short name to search for
     * @return the matching government translation, or null if none found
     */
    GovernmentTranslation findByGovernmentIdAndLanguageShortName(Long governmentId, String languageShortName);
}
