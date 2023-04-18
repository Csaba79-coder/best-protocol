package com.csaba79coder.bestprotocol.model.representative.service;

import com.csaba79coder.bestprotocol.model.Availability;
import com.csaba79coder.bestprotocol.model.PreviousJobTitleTranslationModel;
import com.csaba79coder.bestprotocol.model.RepresentativeAdminModel;
import com.csaba79coder.bestprotocol.model.RepresentativeTranslationManagerModel;
import com.csaba79coder.bestprotocol.model.government.entity.Government;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RepresentativeService {

    private final RepresentativeRepository representativeRepository;
    private final GovernmentTranslationRepository governmentTranslationRepository;
    private final RepresentativeTranslationRepository representativeTranslationRepository;
    private final PreviousJobTitleTranslationRepository previousJobTitleTranslationRepository;

    public RepresentativeAdminModel addNewRepresentative(String languageShortName, String name, String jobTitle, String government, String secretairat, String address, String phoneNumber, String email, MultipartFile image, String note) {
        return Mapper.mapRepresentativeEntityToAdminModel(representativeRepository.save(Mapper.mapFieldIntoEntity(languageShortName, name, jobTitle, government, secretairat, address, phoneNumber, email, image, note)));
    }

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

    public List<RepresentativeAdminModel> renderAllRepresentativesByGovernmentId(String languageShortName, Long governmentId) {
        return representativeRepository.findRepresentativeByGovernmentId(governmentId)
                .stream()
                .map(representative -> getRepresentativeWithTranslation(representative.getId(), languageShortName))
                .collect(Collectors.toList());
    }

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

    public Government findGovernmentByName(String government) {
        return governmentTranslationRepository.findGovernmentByNameContainsIgnoreCase(government)
                .map(GovernmentTranslation::getGovernment)
                .orElseThrow(() -> {
                    String message = String.format("Government: %s was not found", government);
                    log.info(message);
                    return new NoSuchElementException(message);
                });
    }

    private  boolean entityMatchesSearchCriteria(RepresentativeAdminModel model, String search) {
        String lowercaseSearch = search.toLowerCase();
        List<PreviousJobTitleTranslationModel> previousJobTitles = model.getPreviousJobTitle();
        if (previousJobTitles == null) {
            return false;
        }
        return previousJobTitles.stream()
                .anyMatch(jobTitle -> jobTitle.getName().toLowerCase().contains(lowercaseSearch))
                || model.getPhoneNumber().toLowerCase().contains(lowercaseSearch)
                || model.getEmail().toLowerCase().contains(lowercaseSearch)
                || model.getGovernment().getName().toLowerCase().contains(lowercaseSearch)
                || model.getRepresentativeTranslation().getName().toLowerCase().contains(lowercaseSearch)
                || model.getRepresentativeTranslation().getAddress().toLowerCase().contains(lowercaseSearch)
                || model.getRepresentativeTranslation().getCountry().toLowerCase().contains(lowercaseSearch)
                || model.getRepresentativeTranslation().getNote().toLowerCase().contains(lowercaseSearch)
                || model.getRepresentativeTranslation().getSecretairat().toLowerCase().contains(lowercaseSearch)
                || model.getRepresentativeTranslation().getJobTitle().toLowerCase().contains(lowercaseSearch)
                || model.getRepresentativeTranslation().getSecretNote().toLowerCase().contains(lowercaseSearch);
    }
}
