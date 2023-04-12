package com.csaba79coder.bestprotocol.controller;

import com.csaba79coder.bestprotocol.api.MenuApi;
import com.csaba79coder.bestprotocol.model.MenuTranslationModel;
import com.csaba79coder.bestprotocol.model.menu.service.MenuTranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(value = "http://localhost:4200")
@RequiredArgsConstructor
public class MenuTranslationController implements MenuApi {

    private final MenuTranslationService menuTranslationService;

    @Override
    public ResponseEntity<List<MenuTranslationModel>> renderAllMenuTranslations(String languageShortName, String translationKey) {
        return ResponseEntity.status(200).body(menuTranslationService.findAllMenuTranslationByLanguage(languageShortName, translationKey));
    }
}
