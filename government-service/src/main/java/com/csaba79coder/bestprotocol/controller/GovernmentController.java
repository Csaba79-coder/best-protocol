package com.csaba79coder.bestprotocol.controller;

import com.csaba79coder.bestprotocol.api.GovernmentApi;
import com.csaba79coder.bestprotocol.model.GovernmentAdminModel;
import com.csaba79coder.bestprotocol.model.government.service.GovernmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(value = "http://localhost:4200")
@RequiredArgsConstructor
public class GovernmentController implements GovernmentApi {

    private final GovernmentService governmentService;

    @Override
    public ResponseEntity<List<GovernmentAdminModel>> renderAllGovernments() {
        return ResponseEntity.status(200).body(governmentService.findAllGovernments());
    }
}
