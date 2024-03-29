package com.csaba79coder.bestprotocol.model.representative.service;

import com.csaba79coder.bestprotocol.model.Availability;
import com.csaba79coder.bestprotocol.model.PreviousJobTitleTranslationModel;
import com.csaba79coder.bestprotocol.model.RepresentativeAdminModel;
import com.csaba79coder.bestprotocol.model.RepresentativeTranslationManagerModel;
import com.csaba79coder.bestprotocol.model.government.entity.GovernmentTranslation;
import com.csaba79coder.bestprotocol.model.government.persistence.GovernmentTranslationRepository;
import com.csaba79coder.bestprotocol.model.government.persistence.PreviousJobTitleTranslationRepository;
import com.csaba79coder.bestprotocol.model.representative.entity.Representative;
import com.csaba79coder.bestprotocol.model.representative.entity.RepresentativeTranslation;
import com.csaba79coder.bestprotocol.model.representative.persistence.RepresentativeRepository;
import com.csaba79coder.bestprotocol.model.representative.persistence.RepresentativeTranslationRepository;
import com.csaba79coder.bestprotocol.util.ImageUtil;
import com.csaba79coder.bestprotocol.util.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

/**

 Service class that handles operations related to government representatives.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RepresentativeService {

    private final RepresentativeRepository representativeRepository;
    private final GovernmentTranslationRepository governmentTranslationRepository;
    private final RepresentativeTranslationRepository representativeTranslationRepository;
    private final PreviousJobTitleTranslationRepository previousJobTitleTranslationRepository;

    // TODO implement functionality to add a new representative
    /*public RepresentativeAdminModel addNewRepresentative(String languageShortName, String name, String jobTitle, String government, String secretairat, String address, String phoneNumber, String email, MultipartFile image, String note) {
        return Mapper.mapRepresentativeEntityToAdminModel(representativeRepository.save(Mapper.mapFieldIntoEntity(languageShortName, name, jobTitle, government, secretairat, address, phoneNumber, email, image, note)));
    }*/

    /**
     * Returns a list of all representatives, with their translations, optionally filtered by search criteria.
     *
     * @param languageShortName The language short name to use for translations.
     * @param search The search criteria to filter the results by. Can be null or empty.
     * @return A list of {@link RepresentativeAdminModel} objects representing all the representatives that match the search criteria (if any).
     */
    public List<RepresentativeAdminModel> renderAllRepresentatives(String languageShortName, String search) {
        List<RepresentativeAdminModel> representativeAdminModels = representativeRepository.findAll()
                .stream()
                .map(representative -> getRepresentativeWithTranslation(representative.getId(), languageShortName))
                .toList();
        if (search != null && !search.trim().isEmpty() && !search.trim().isBlank()) {
            String lowercaseSearch = search.toLowerCase();
            representativeAdminModels = representativeAdminModels.stream()
                    .filter(model -> entityMatchesSearchCriteria(model, lowercaseSearch))
                    .collect(Collectors.toList());
        }
        return representativeAdminModels;
    }

    /**
     Retrieves all representatives by government ID and returns a list of {@link RepresentativeAdminModel}s,

     which include translations in the given language and are filtered by the search string.

     @param languageShortName The language short name to be used for translations.

     @param governmentId The ID of the government to retrieve representatives for.

     @param search The search string to be used for filtering the results.

     @return A list of {@link RepresentativeAdminModel}s that match the search criteria.
     */
    public List<RepresentativeAdminModel> renderAllRepresentativesByGovernmentId(String languageShortName, Long governmentId, String search) {
        List<RepresentativeAdminModel> representativeAdminModels = representativeRepository.findRepresentativeByGovernmentId(governmentId)
                .stream()
                .map(representative -> getRepresentativeWithTranslation(representative.getId(), languageShortName))
                .collect(Collectors.toList());

        if (search != null && !search.trim().isEmpty()) {
            String lowercaseSearch = search.trim().toLowerCase();
            representativeAdminModels = representativeAdminModels.stream()
                    .filter(model -> entityMatchesSearchCriteria(model, lowercaseSearch))
                    .collect(Collectors.toList());
        }

        return representativeAdminModels;
    }

    /**
     * Returns a RepresentativeAdminModel object with the specified UUID and language short name.
     *
     * @param representativeId The UUID of the representative to retrieve.
     * @param languageShortName The language short name for the translation to include in the model.
     * @return A RepresentativeAdminModel object with the specified UUID and language short name.
     * @throws NoSuchElementException if the representative with the given UUID does not exist in the database.
     */
    private RepresentativeAdminModel getRepresentativeWithTranslation(UUID representativeId, String languageShortName) {
        Representative currentRepresentative = representativeRepository.findById(representativeId).orElseThrow(() -> new NoSuchElementException("Representative not found"));
        GovernmentTranslation governmentTranslation = governmentTranslationRepository.findByGovernmentIdAndLanguageShortName(currentRepresentative.getGovernment().getId(), languageShortName);
        RepresentativeTranslation currentTranslation = representativeTranslationRepository.findRepresentativeTranslationByLanguageShortNameAndRepresentativeId(languageShortName, currentRepresentative.getId());
        if (currentTranslation != null) {
            RepresentativeTranslationManagerModel model = new RepresentativeTranslationManagerModel();
            model.setId(currentTranslation.getId());
            model.setName(currentTranslation.getName());
            model.setLanguageShortName(currentTranslation.getLanguageShortName());
            model.setAddress(currentTranslation.getAddress());
            model.setJobTitle(currentTranslation.getJobTitle());
            model.setSecretairat(currentTranslation.getSecretairat());
            model.setNote(currentTranslation.getNote());
            model.setSecretNote(currentTranslation.getSecretNote());
            model.setCountry(currentTranslation.getCountry());
            return new RepresentativeAdminModel()
                    .id(currentRepresentative.getId())
                    .createdAt(String.valueOf(currentRepresentative.getCreatedAt()))
                    .updatedAt(String.valueOf(currentRepresentative.getUpdatedAt()))
                    .createdBy(currentRepresentative.getCreatedBy())
                    .updatedBy(currentRepresentative.getUpdatedBy())
                    .representativeTranslation(model)
                    .government(Mapper.mapGovernmentTranslationToAdminModel(governmentTranslation))
                    .phoneNumber(currentRepresentative.getPhoneNumber())
                    .email(currentRepresentative.getEmail())
                    .image(ImageUtil.decompressImage(currentRepresentative.getImage()))
                    .previousJobTitle(previousJobTitleTranslationRepository.findByRepresentativeAndLanguageShortName(currentRepresentative, languageShortName)
                            .stream()
                            .map(previousJobTitleTranslation -> {
                                PreviousJobTitleTranslationModel jobTitle = new PreviousJobTitleTranslationModel();
                                jobTitle.setId(previousJobTitleTranslation.getId());
                                jobTitle.setName(previousJobTitleTranslation.getName());
                                jobTitle.setLanguageShortName(previousJobTitleTranslation.getLanguageShortName());
                                return jobTitle;
                            })
                            .toList())
                    .availability(Availability.valueOf(currentRepresentative.getAvailability().name()));
        } else {
            return new RepresentativeAdminModel()
                    .id(currentRepresentative.getId())
                    .createdAt(String.valueOf(currentRepresentative.getCreatedAt()))
                    .updatedAt(String.valueOf(currentRepresentative.getUpdatedAt()))
                    .createdBy(currentRepresentative.getCreatedBy())
                    .updatedBy(currentRepresentative.getUpdatedBy())
                    .government(Mapper.mapGovernmentTranslationToAdminModel(governmentTranslation))
                    .phoneNumber(currentRepresentative.getPhoneNumber())
                    .email(currentRepresentative.getEmail())
                    .image(ImageUtil.decompressImage(currentRepresentative.getImage()))
                    .availability(Availability.valueOf(currentRepresentative.getAvailability().name()));
        }
    }

    // TODO refactor regarding the changes, at the moment not used!
    /*public Government findGovernmentByName(String government) {
        return governmentTranslationRepository.findGovernmentByNameContainsIgnoreCase(government)
                .map(GovernmentTranslation::getGovernment)
                .orElseThrow(() -> {
                    String message = String.format("Government: %s was not found", government);
                    log.info(message);
                    return new NoSuchElementException(message);
                });
    }*/

    /**

     Checks if the given RepresentativeAdminModel object matches the specified search criteria.
     If any of the model's fields contains the given search string in a case-insensitive manner, returns true.
     In the if it was required to check null value as well, as translation is not a must!
     Search in case of a mobile number can be only a part of it, as it removes all hyphens from the phone number
     @param model The RepresentativeAdminModel object to check.
     @param search The search string to check against the fields of the model.
     @return true if the model matches the search criteria, false otherwise.
     */
    private boolean entityMatchesSearchCriteria(RepresentativeAdminModel model, String search) {
        String lowercaseSearch = search.toLowerCase();
        if (model.getGovernment() != null && model.getGovernment().getName() != null && model.getGovernment().getName().toLowerCase().contains(lowercaseSearch)) {
            return true;
        }
        if (model.getRepresentativeTranslation() != null) {
            RepresentativeTranslationManagerModel translation = model.getRepresentativeTranslation();
            if (translation.getName() != null && translation.getName().toLowerCase().contains(lowercaseSearch)) {
                return true;
            }
            if (translation.getAddress() != null && translation.getAddress().toLowerCase().contains(lowercaseSearch)) {
                return true;
            }
            if (translation.getCountry() != null && translation.getCountry().toLowerCase().contains(lowercaseSearch)) {
                return true;
            }
            if (translation.getNote() != null && translation.getNote().toLowerCase().contains(lowercaseSearch)) {
                return true;
            }
            if (translation.getSecretairat() != null && translation.getSecretairat().toLowerCase().contains(lowercaseSearch)) {
                return true;
            }
            if (translation.getJobTitle() != null && translation.getJobTitle().toLowerCase().contains(lowercaseSearch)) {
                return true;
            }
            if (translation.getSecretNote() != null && translation.getSecretNote().toLowerCase().contains(lowercaseSearch)) {
                return true;
            }
        }
        if (model.getPreviousJobTitle() != null) {
            for (PreviousJobTitleTranslationModel jobTitle : model.getPreviousJobTitle()) {
                if (jobTitle != null && jobTitle.getName() != null && jobTitle.getName().toLowerCase().contains(lowercaseSearch)) {
                    return true;
                }
            }
        }
        // removes all hyphens from the phone number
        if (model.getPhoneNumber() != null && model.getPhoneNumber().replaceAll("-", "").toLowerCase().contains(lowercaseSearch)) {
            return true;
        }
        if (model.getEmail() != null && model.getEmail().toLowerCase().contains(lowercaseSearch)) {
            return true;
        }
        return false;
    }
}
