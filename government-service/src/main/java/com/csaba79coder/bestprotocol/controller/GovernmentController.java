package com.csaba79coder.bestprotocol.controller;

import com.csaba79coder.bestprotocol.api.GovernmentApi;
import com.csaba79coder.bestprotocol.model.GovernmentTranslationModel;
import com.csaba79coder.bestprotocol.model.government.service.GovernmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**

 Rest Controller that handles requests related to governments.

 Allows GET requests for all governments, and for governments by ID in a specific language.
 */
@RestController
@CrossOrigin(value = "http://localhost:4200")
@RequiredArgsConstructor
public class GovernmentController implements GovernmentApi {

    private final GovernmentService governmentService;

    /**

     Returns a ResponseEntity containing a list of GovernmentTranslationModel objects
     for all the governments available in the database with the specified language short name.
     @param languageShortName The language short name to use for the translations.
     @return A ResponseEntity containing a list of GovernmentTranslationModel objects.
     HTTP status code of 200 (OK) if the request was successful.
     */
    @Override
    public ResponseEntity<List<GovernmentTranslationModel>> renderAllGovernments(String languageShortName) {
        return ResponseEntity.status(200).body(governmentService.findAllGovernmentsByLangAndGovernmentId(languageShortName));
    }

    /**

     Retrieves all the translations for a specific government given its ID and a language short name.
     @param languageShortName The language short name to retrieve the translations for.
     @param governmentId The ID of the government to retrieve the translations for.
     @return A ResponseEntity containing a list of GovernmentTranslationModel objects and an HTTP status code of 200 (OK) if the request was successful.
     */
    @Override
    public ResponseEntity<List<GovernmentTranslationModel>> renderAllGovernmentsById(String languageShortName, Long governmentId) {
        return ResponseEntity.status(200).body(governmentService.findAllGovernmentsByLangAndGovernmentId(languageShortName, governmentId));
    }
}
