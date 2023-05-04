package com.csaba79coder.loggingservice.util;

import com.csaba79coder.bestprotocol.model.LoggingModel;
import com.csaba79coder.loggingservice.model.entity.Logging;
import org.modelmapper.ModelMapper;

/**
 * responsible for logging the whole application and mapping entity to model
 */
public class Mapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    /**
     * @param entity
     * @return a LoggingModel object from a Logging entity
     */
    public static LoggingModel mapLoggingEntityToModel(Logging entity) {
        LoggingModel model = new LoggingModel();
        modelMapper.map(entity, model);
        return model;
    }

    private Mapper() {
    }
}
