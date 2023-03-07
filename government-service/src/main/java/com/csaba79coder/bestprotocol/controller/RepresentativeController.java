package com.csaba79coder.bestprotocol.controller;

import com.csaba79coder.bestprotocol.api.GovernmentRepresentativeApi;
import com.csaba79coder.bestprotocol.model.RepresentativeModel;
import com.csaba79coder.bestprotocol.model.representative.service.RepresentativeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(value = "http://localhost:4200")
@RequiredArgsConstructor
public class RepresentativeController implements GovernmentRepresentativeApi {

    private final RepresentativeService representativeService;

    @Override
    public ResponseEntity<RepresentativeModel> addNewRepresentative(String name, String jobTitle, String address, String phoneNumber, String email, MultipartFile image, String note) {
        return ResponseEntity.status(201).body(representativeService.addNewRepresentative(name, jobTitle, address, phoneNumber, email, image, note));
    }

    @Override
    public ResponseEntity<List<RepresentativeModel>> renderAllRepresentatives() {
        return ResponseEntity.status(200).body(representativeService.renderAllRepresentatives());
    }
}
