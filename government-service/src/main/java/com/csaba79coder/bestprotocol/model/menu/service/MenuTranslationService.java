package com.csaba79coder.bestprotocol.model.menu.service;

import com.csaba79coder.bestprotocol.model.MenuTranslationModel;
import com.csaba79coder.bestprotocol.model.menu.persistence.MenuTranslationRepository;
import com.csaba79coder.bestprotocol.util.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuTranslationService {

    private final MenuTranslationRepository menuTranslationRepository;

    public List<MenuTranslationModel> findAllMenuTranslationByLanguage(String languagesShortName, String translationKey) {
        return menuTranslationRepository.findByLanguageShortNameAndTranslationKey(languagesShortName, translationKey)
                .stream()
                .map(Mapper::mapMenuTranslationEntityToModel)
                .collect(Collectors.toList());
    }
}
