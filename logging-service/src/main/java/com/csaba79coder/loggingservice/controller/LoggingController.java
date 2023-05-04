package com.csaba79coder.loggingservice.controller;

import com.csaba79coder.bestprotocol.api.LogApi;
import com.csaba79coder.bestprotocol.model.LoggingModel;
import com.csaba79coder.bestprotocol.model.LoggingNewModel;
import com.csaba79coder.loggingservice.model.service.LoggingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * responsible for logging the whole application (rest endpoint definitions implementing api interface)
 */
@RestController
@RequiredArgsConstructor
public class LoggingController implements LogApi {

    private final LoggingService loggingService;

    /**
     * @param body
     * @return a LoggingModel object from a new created Logging entity
     */
    @Override
    public ResponseEntity<LoggingModel> addNewLog(LoggingNewModel body) {
        return ResponseEntity.status(201).body(loggingService.addNewLog(body));
    }

    /**
     * @return list of all logs
     */
    @Override
    public ResponseEntity<List<LoggingModel>> renderAllLogs() {
        return ResponseEntity.status(200).body(loggingService.renderAllLogs());
    }
}
