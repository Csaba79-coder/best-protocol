package com.csaba79coder.bestprotocol.model.representative.service;

import com.csaba79coder.bestprotocol.model.Availability;
import com.csaba79coder.bestprotocol.model.RepresentativeAdminModel;
import com.csaba79coder.bestprotocol.model.government.entity.Government;
import com.csaba79coder.bestprotocol.model.government.entity.GovernmentTranslation;
import com.csaba79coder.bestprotocol.model.government.persistence.GovernmentRepository;
import com.csaba79coder.bestprotocol.model.government.persistence.GovernmentTranslationRepository;
import com.csaba79coder.bestprotocol.model.representative.entity.Representative;
import com.csaba79coder.bestprotocol.model.representative.persistence.RepresentativeRepository;
import com.csaba79coder.bestprotocol.util.ImageUtil;
import com.csaba79coder.bestprotocol.util.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RepresentativeService {

    private final RepresentativeRepository representativeRepository;
    private final GovernmentRepository governmentRepository;
    private final GovernmentTranslationRepository governmentTranslationRepository;

    public RepresentativeAdminModel addNewRepresentative(String languageShortName, String name, String jobTitle, String government, String secretairat, String address, String phoneNumber, String email, MultipartFile image, String note) {
        return Mapper.mapRepresentativeEntityToAdminModel(representativeRepository.save(Mapper.mapFieldIntoEntity(languageShortName, name, jobTitle, government, secretairat, address, phoneNumber, email, image, note)));
    }

    public List<RepresentativeAdminModel> renderAllRepresentatives(String languageShortName) {
        return representativeRepository.findAllByLanguageShortName(languageShortName)
                .stream()
                .map(representative -> getRepresentativeWithTranslation(languageShortName))
                .collect(Collectors.toList());
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

    public List<RepresentativeAdminModel> renderAllRepresentativesByGovernmentId(String languageShortName, Long governmentId) {
        return representativeRepository.findRepresentativeByLanguageShortNameAndGovernmentId(languageShortName, governmentId)
                .stream()
                .map(representative -> getRepresentativeWithTranslation(languageShortName))
                .collect(Collectors.toList());
    }

    private RepresentativeAdminModel getRepresentativeWithTranslation(String languageShortName) {
        Representative currentRepresentative = representativeRepository.findRepresentativeByLanguageShortName(languageShortName);
        Optional<GovernmentTranslation> governmentTranslation = governmentTranslationRepository.findGovernmentTranslationById(currentRepresentative.getGovernment().getId());
        governmentTranslation.ifPresent(translation -> currentRepresentative.setGovernment(translation.getGovernment()));
        return new RepresentativeAdminModel()
                .id(currentRepresentative.getId())
                .createdAt(String.valueOf(currentRepresentative.getCreatedAt()))
                .updatedAt(String.valueOf(currentRepresentative.getUpdatedAt()))
                .createdBy(currentRepresentative.getCreatedBy())
                .updatedBy(currentRepresentative.getUpdatedBy())
                .name(currentRepresentative.getName())
                .jobTitle(currentRepresentative.getJobTitle())
                .government(Mapper.mapGovernmentTranslationToAdminModel(governmentTranslation.get()))
                .secretairat(currentRepresentative.getSecretairat())
                .address(currentRepresentative.getAddress())
                .phoneNumber(currentRepresentative.getPhoneNumber())
                .email(currentRepresentative.getEmail())
                .image(ImageUtil.decompressImage(currentRepresentative.getImage()))
                // TODO add to api contract the secret note!
                .note(currentRepresentative.getNote())
                .availability(Availability.valueOf(currentRepresentative.getAvailability().name()));
    }
}
