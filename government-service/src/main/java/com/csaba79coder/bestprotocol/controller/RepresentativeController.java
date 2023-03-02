package com.csaba79coder.bestprotocol.controller;

import com.csaba79coder.api.GovernmentRepresentativeApi;
import com.csaba79coder.bestprotocol.model.representative.service.RepresentativeService;
import com.csaba79coder.model.NewRepresentativeModel;
import com.csaba79coder.model.RepresentativeModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(value = "http://localhost:4200")
@RequiredArgsConstructor
public class RepresentativeController implements GovernmentRepresentativeApi {

    private final RepresentativeService representativeService;

    @Override
    public ResponseEntity<List<RepresentativeModel>> renderAllRepresentatives() {
        return null;
    }

    @Override
    public ResponseEntity<RepresentativeModel> representativeApiAddNewRepresentative(NewRepresentativeModel body) {
        return null;
    }
}
