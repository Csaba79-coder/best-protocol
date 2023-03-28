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

@Service
@RequiredArgsConstructor
@Slf4j
public class GovernmentService {

    private final GovernmentTranslationRepository governmentTranslationRepository;

    public List<GovernmentTranslationModel> findAllGovernmentsByLang(String lang) {
        // TODO from Translation creating immediately the Model!
        return governmentTranslationRepository.findGovernmentTranslationByLanguageShortName(lang)
                .stream()
                .map(Mapper::mapGovernmentTranslationToGovernmentAdminModel)
                .collect(Collectors.toList());
    }

    public GovernmentTranslation findGovernmentById(Long id) {
        return governmentTranslationRepository.findGovernmentTranslationById(id)
                .orElseThrow(() -> {
                    String message = String.format("Government with id: %s was not found", id);
                    log.info(message);
                    return new NoSuchElementException(message);
                });
    }
}
