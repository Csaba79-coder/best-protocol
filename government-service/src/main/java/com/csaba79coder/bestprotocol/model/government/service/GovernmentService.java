package com.csaba79coder.bestprotocol.model.government.service;

import com.csaba79coder.bestprotocol.model.GovernmentAdminModel;
import com.csaba79coder.bestprotocol.model.government.entity.GovernmentTranslation;
import com.csaba79coder.bestprotocol.model.government.persistence.GovernmentRepository;
import com.csaba79coder.bestprotocol.model.government.persistence.GovernmentTranslationRepository;
import com.csaba79coder.bestprotocol.util.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GovernmentService {

    private final GovernmentRepository governmentRepository;
    private final GovernmentTranslationRepository governmentTranslationRepository;

    public List<GovernmentAdminModel> findAllGovernments() {
        return governmentRepository.findAll()
                .stream()
                .map(Mapper::mapGovernmentEntityToAdminModel)
                .collect(Collectors.toList());
    }

    public List<GovernmentAdminModel> findAllGovernmentsByLang(String lang) {
        return governmentTranslationRepository.findGovernmentTranslationByLanguageShortName(lang)
                .stream()
                .map(GovernmentTranslation::getGovernment)
                .map(Mapper::mapGovernmentEntityToAdminModel)
                .collect(Collectors.toList());
    }
}
