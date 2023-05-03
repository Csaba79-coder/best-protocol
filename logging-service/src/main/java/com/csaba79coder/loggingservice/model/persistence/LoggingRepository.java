package com.csaba79coder.loggingservice.model.persistence;

import com.csaba79coder.loggingservice.model.entity.Logging;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * responsible for logging the whole application as the persistence layer
 */
@Repository
public interface LoggingRepository extends MongoRepository<Logging, String> {
}
