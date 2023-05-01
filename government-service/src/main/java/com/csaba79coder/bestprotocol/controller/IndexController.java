package com.csaba79coder.bestprotocol.controller;

import com.csaba79coder.bestprotocol.api.IndexApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(value = "http://localhost:4200")
public class IndexController implements IndexApi {

    private final String INDEX = "index.html";

    @Override
    public ResponseEntity<String> renderIndexPage() {
        return ResponseEntity.status(200).body(INDEX);
    }

    @Override
    public ResponseEntity<String> renderIndexPage_0() {
        return ResponseEntity.status(200).body(INDEX);
    }
}
