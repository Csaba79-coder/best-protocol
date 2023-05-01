package com.csaba79coder.bestprotocol.controller;

import com.csaba79coder.bestprotocol.api.GovernmentRepresentativeApi;
import com.csaba79coder.bestprotocol.model.RepresentativeAdminModel;
import com.csaba79coder.bestprotocol.model.representative.service.RepresentativeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**

 REST controller for managing government representatives.
 Exposes API endpoints to retrieve and manipulate government representative data.
 */
@RestController
@CrossOrigin(value = "http://localhost:4200")
@RequiredArgsConstructor
public class RepresentativeController implements GovernmentRepresentativeApi {

    private final RepresentativeService representativeService;


    // TODO: Implement the following methods:
    @Override
    public ResponseEntity<RepresentativeAdminModel> addNewRepresentative(String languageShortName, String name, String government, String secretairat, String jobTitle, String address, String country, String phoneNumber, String email, MultipartFile image, String note) {
        return null;
    }

    /**

     Retrieves all representatives for a given government ID, filtered by search criteria.
     @param governmentId The ID of the government to retrieve representatives for.
     @param languageShortName The language short name for the translation to include in the model.
     @param search The search criteria to filter the results by.
     @param page The page number of results to retrieve.
     @param size The number of results per page to retrieve.
     @return A ResponseEntity containing a List of RepresentativeAdminModel objects that match the specified criteria.
     */
    @Override
    public ResponseEntity<List<RepresentativeAdminModel>> findByGovernmentId(Long governmentId, String languageShortName, String search, Integer page, Integer size) {
        return ResponseEntity.status(200).body(representativeService.renderAllRepresentativesByGovernmentId(languageShortName, governmentId, search));
    }

    /**
     * Retrieves a list of all representatives based on the specified search criteria and language.
     *
     * @param languageShortName the short name of the language to use for translation
     * @param search the search criteria to use when filtering the list of representatives
     * @return a ResponseEntity containing a list of RepresentativeAdminModel objects, with an HTTP status of 200 (OK)
     */
    @Override
    public ResponseEntity<List<RepresentativeAdminModel>> renderAllRepresentatives(String languageShortName, String search) {
        return ResponseEntity.status(200).body(representativeService.renderAllRepresentatives(languageShortName, search));
    }
}
