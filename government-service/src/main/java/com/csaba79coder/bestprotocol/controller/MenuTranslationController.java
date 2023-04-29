package com.csaba79coder.bestprotocol.controller;

import com.csaba79coder.bestprotocol.api.MenuApi;
import com.csaba79coder.bestprotocol.model.MenuTranslationModel;
import com.csaba79coder.bestprotocol.model.menu.service.MenuTranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**

 Controller for retrieving menu translation information.

 */
@RestController
@CrossOrigin(value = "http://localhost:4200")
@RequiredArgsConstructor
public class MenuTranslationController implements MenuApi {

    private final MenuTranslationService menuTranslationService;

    /**

     Returns a list of MenuTranslationModel objects for the specified language short name and translation key.
     @param languageShortName The language short name for the translations to retrieve.
     @param translationKey The translation key for the translations to retrieve.
     @return A ResponseEntity containing a list of MenuTranslationModel objects.
     HTTP status code of 200 (OK) if the request was successful.
     */
    @Override
    public ResponseEntity<List<MenuTranslationModel>> renderAllMenuTranslations(String languageShortName, String translationKey) {
        return ResponseEntity.status(200).body(menuTranslationService.findAllMenuTranslationByLanguage(languageShortName, translationKey));
    }
}
