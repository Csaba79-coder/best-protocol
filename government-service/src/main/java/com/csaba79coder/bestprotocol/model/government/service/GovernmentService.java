package com.csaba79coder.bestprotocol.model.government.service;

import com.csaba79coder.bestprotocol.model.GovernmentTranslationModel;
import com.csaba79coder.bestprotocol.model.government.entity.GovernmentTranslation;
import com.csaba79coder.bestprotocol.model.government.persistence.GovernmentTranslationRepository;
import com.csaba79coder.bestprotocol.util.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**

 Service class for managing Government and its translations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GovernmentService {

    private final GovernmentTranslationRepository governmentTranslationRepository;

    /**

     Finds all governments in the specified language and maps them to GovernmentTranslationModel objects.
     @param lang the language code of the desired language
     @return a list of GovernmentTranslationModel objects
     */
    public List<GovernmentTranslationModel> findAllGovernmentsByLangAndGovernmentId(String lang) {
        // from Translation creating immediately the Model!
        return governmentTranslationRepository.findGovernmentTranslationByLanguageShortName(lang)
                .stream()
                .map(Mapper::mapGovernmentTranslationToGovernmentAdminModel)
                .collect(Collectors.toList());
    }

    /**

     Returns a list of {@link GovernmentTranslationModel} objects that contain translations for a specific language and government ID.
     @param lang the language short name for the translations
     @param id the ID of the government entity to search for translations
     @return a list of {@link GovernmentTranslationModel} objects that match the search criteria
     */
    public List<GovernmentTranslationModel> findAllGovernmentsByLangAndGovernmentId(String lang, Long id) {
        // TODO from Translation creating immediately the Model!
        return governmentTranslationRepository.findGovernmentTranslationByLanguageShortNameAndGovernmentId(lang, id)
                .stream()
                .map(Mapper::mapGovernmentTranslationToGovernmentAdminModel)
                .collect(Collectors.toList());
    }

    // not in usage at the moment, need it for later!
    /**

     Returns the {@link GovernmentTranslation} object with the given ID, or throws a {@link NoSuchElementException} if no
     such object exists.
     @param id the ID of the {@link GovernmentTranslation} to retrieve
     @return the {@link GovernmentTranslation} object with the given ID
     @throws NoSuchElementException if no {@link GovernmentTranslation} with the given ID exists
     */
    public GovernmentTranslation findGovernmentById(Long id) {
        return governmentTranslationRepository.findGovernmentTranslationById(id)
                .orElseThrow(() -> {
                    String message = String.format("Government with id: %s was not found", id);
                    log.info(message);
                    return new NoSuchElementException(message);
                });
    }
}
