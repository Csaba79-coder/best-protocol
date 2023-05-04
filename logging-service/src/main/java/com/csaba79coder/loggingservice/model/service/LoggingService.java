package com.csaba79coder.loggingservice.model.service;

import com.csaba79coder.bestprotocol.model.LoggingModel;
import com.csaba79coder.bestprotocol.model.LoggingNewModel;
import com.csaba79coder.loggingservice.model.entity.Logging;
import com.csaba79coder.loggingservice.model.persistence.LoggingRepository;
import com.csaba79coder.loggingservice.util.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * responsible for logging the whole application as the service layer
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LoggingService {

    private final LoggingRepository loggingRepository;

    /**
     * @param body
     * @return a LoggingModel object from a new created Logging entity
     */
    public LoggingModel addNewLog(LoggingNewModel body) {
        // displaying date in mm-dd-yyyy hh:mm:ss format
        Format f = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");
        String timeNow = f.format(new Date());
        Logging entity = new Logging();
        entity.setMessage(body.getMessage());
        entity.setTimestamp(timeNow);
        entity.setLevel(entity.getLevel());
        entity.setServiceName(body.getServiceName());
        return Mapper.mapLoggingEntityToModel(loggingRepository.save(entity));
    }

    /**
     * @return list of all logs
     */
    public List<LoggingModel> renderAllLogs() {
        return loggingRepository.findAll()
                .stream()
                .map(Mapper::mapLoggingEntityToModel)
                .collect(Collectors.toList());
    }
}
