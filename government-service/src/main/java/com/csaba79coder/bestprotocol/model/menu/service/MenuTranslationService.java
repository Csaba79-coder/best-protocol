package com.csaba79coder.bestprotocol.model.menu.service;

import com.csaba79coder.bestprotocol.model.MenuTranslationModel;
import com.csaba79coder.bestprotocol.model.menu.persistence.MenuTranslationRepository;
import com.csaba79coder.bestprotocol.util.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class that provides methods for retrieving and manipulating the Menu tranlation.
 */
@Service
@RequiredArgsConstructor
public class MenuTranslationService {

    private final MenuTranslationRepository menuTranslationRepository;

    /**
     * Retrieves all {@link MenuTranslationModel} entities with the specified language short name and translation key.
     *
     * @param languageShortName The language short name.
     * @param translationKey    The translation key.
     * @return A list of {@link MenuTranslationModel} entities that match the specified criteria.
     */
    public List<MenuTranslationModel> findAllMenuTranslationByLanguage(String languageShortName, String translationKey) {
        return menuTranslationRepository.findByLanguageShortNameAndTranslationKey(languageShortName, translationKey)
                .stream()
                .map(Mapper::mapMenuTranslationEntityToModel)
                .collect(Collectors.toList());
    }
}
